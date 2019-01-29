package com.hendraanggrian.defaults

import com.google.auto.common.MoreElements.asType
import com.google.common.collect.LinkedHashMultimap.create
import com.hendraanggrian.defaults.internal.DefaultBindingSpec
import com.hendraanggrian.defaults.internal.measuredName
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.SourceVersion.latestSupported
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

class DefaultsProcessor : AbstractProcessor() {

    private lateinit var _filer: Filer

    override fun getSupportedSourceVersion(): SourceVersion = latestSupported()

    override fun getSupportedAnnotationTypes(): Set<String> =
        setOf(Default::class.java.canonicalName)

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        _filer = processingEnv.filer
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // preparing elements
        val multimap = create<TypeElement, Element>()
        val measuredClassNames = mutableSetOf<String>()
        roundEnv.getElementsAnnotatedWith(Default::class.java).forEach { element ->
            val typeElement = asType(element.enclosingElement)
            multimap.put(typeElement, element)
            measuredClassNames.add(typeElement.measuredName)
        }
        // generate classes
        multimap.keySet().map { key ->
            DefaultBindingSpec(key)
                .superclass(measuredClassNames)
                .statement(multimap[key])
                .toJavaFile()
        }.forEach { javaFile ->
            try {
                javaFile.writeTo(_filer)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        return false
    }
}