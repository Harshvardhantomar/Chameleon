/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.annotations.processing.generation.impl.nukkit;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import dev.hypera.chameleon.annotations.PlatformDependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.Plugin.Platform;
import dev.hypera.chameleon.annotations.processing.generation.Generator;
import dev.hypera.chameleon.annotations.utils.MapBuilder;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

/**
 * Nukkit plugin main class and 'nukkit.yml' description file generator.
 */
public class NukkitGenerator extends Generator {

    private static final @NotNull String DESCRIPTION_FILE = "nukkit.yml";

    /**
     * Generate Nukkit plugin main class and 'nukkit.yml' description file.
     *
     * @param data   {@link Plugin} data
     * @param plugin Chameleon plugin main class
     * @param env    Processing environment
     *
     * @throws Exception if something goes wrong while creating the files.
     */
    @Override
    public void generate(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env) throws Exception {
        MethodSpec loadSpec = MethodSpec.methodBuilder("onLoad")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .beginControlFlow("try")
            .addStatement(createPluginData(data))
            .addStatement("this.$N = $T.create($T.class, this, $N).load()", "chameleon", clazz("dev.hypera.chameleon.platforms.nukkit", "NukkitChameleon"), plugin, "pluginData")
            .nextControlFlow("catch ($T ex)", ChameleonInstantiationException.class)
            .addStatement("$N.printStackTrace()", "ex")
            .endControlFlow()
            .build();

        MethodSpec enableSpec = MethodSpec.methodBuilder("onEnable")
            .addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
            .addStatement("this.$N.onEnable()", "chameleon").build();

        MethodSpec disableSpec = MethodSpec.methodBuilder("onDisable")
            .addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
            .addStatement("this.$N.onDisable()", "chameleon").build();

        TypeSpec nukkitMainClassSpec = TypeSpec.classBuilder(plugin.getSimpleName() + "Nukkit")
            .addModifiers(Modifier.PUBLIC)
            .superclass(clazz("cn.nukkit.plugin", "PluginBase"))
            .addField(FieldSpec.builder(clazz("dev.hypera.chameleon.platforms.nukkit", "NukkitChameleon"), "chameleon", Modifier.PRIVATE).build())
            .addMethod(loadSpec)
            .addMethod(enableSpec)
            .addMethod(disableSpec)
            .build();

        String packageName = Objects.requireNonNull((PackageElement) plugin.getEnclosingElement()).getQualifiedName().toString();
        if (packageName.endsWith("core") || packageName.endsWith("common")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        }
        packageName = packageName + ".platform.nukkit";

        JavaFile.builder(packageName, nukkitMainClassSpec).indent(INDENT).build().writeTo(env.getFiler());
        generateDescriptionFile(data, plugin, env, packageName);
    }

    private void generateDescriptionFile(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env, @NotNull String packageName) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(env.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", DESCRIPTION_FILE).toUri()))) {
            new Yaml().dump(new MapBuilder<String, Object>().add("name", data.name().isEmpty() ? data.id() : data.name())
                .add("main", packageName + "." + plugin.getSimpleName() + "Nukkit")
                .add("version", data.version())
                .add("api", Collections.singletonList("1.0.5"))
                .add("load", "STARTUP")
                .add("author", data.authors().length > 0 ? String.join(", ", data.authors()) : "Unknown")
                .add("authors", data.authors())
                .add("website", data.url())
                .add("depend", Arrays.stream(data.dependencies()).filter(d -> !d.soft() && (d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.NUKKIT))).map(PlatformDependency::name).collect(Collectors.toList()))
                .add("softdepend", Arrays.stream(data.dependencies()).filter(d -> d.soft() && (d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.NUKKIT))).map(PlatformDependency::name).collect(Collectors.toList()))
                .add("description", data.description()), writer);
        }
    }


}
