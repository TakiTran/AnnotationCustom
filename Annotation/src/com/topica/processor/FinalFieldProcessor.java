package com.topica.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({ "com.topica.annotation.FinalField" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class FinalFieldProcessor extends AbstractProcessor {
	public static final String MESSAGE_MODIFIER = "Field modifiers must be public and final.";
	public static final String MESSAGE_NAME = "The field name invalid";
	private Filer filer;
	private Messager messager;

	@Override
	public void init(ProcessingEnvironment env) {
		filer = env.getFiler();
		messager = env.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {
			Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
			for (Element element : elements) {
				String nameField = element.getSimpleName().toString();
				Set<Modifier> modifiers = element.getModifiers();
				if (!(modifiers.contains(Modifier.FINAL) && modifiers.contains(Modifier.PUBLIC))) {
					messager.printMessage(Diagnostic.Kind.ERROR, MESSAGE_MODIFIER, element);
				}
				if (!nameField.equals(nameField.toUpperCase())) {
					messager.printMessage(Diagnostic.Kind.ERROR, MESSAGE_NAME, element);
				}
			}
		}
		return true;
	}
}
