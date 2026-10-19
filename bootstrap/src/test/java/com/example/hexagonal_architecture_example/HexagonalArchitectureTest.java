package com.example.hexagonal_architecture_example;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * 헥사고날 아키텍처의 의존 방향(바깥 → 안쪽)을 테스트로 강제한다.
 */
@AnalyzeClasses(packagesOf = HexagonalArchitectureTest.class, importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

	@ArchTest
	static final ArchRule domainDependsOnNothingButJava = classes()
			.that().resideInAPackage("..domain..")
			.should().onlyDependOnClassesThat().resideInAnyPackage("java..", "..domain..");

	@ArchTest
	static final ArchRule applicationDoesNotDependOnAdapters = noClasses()
			.that().resideInAPackage("..application..")
			.should().dependOnClassesThat().resideInAPackage("..adapter..");

	@ArchTest
	static final ArchRule webAdapterUsesOnlyInboundPorts = noClasses()
			.that().resideInAPackage("..adapter.in..")
			.should().dependOnClassesThat().resideInAnyPackage("..application.service..", "..application.port.out..");

	@ArchTest
	static final ArchRule inboundAndOutboundAdaptersAreIndependent = noClasses()
			.that().resideInAPackage("..adapter.in..")
			.should().dependOnClassesThat().resideInAPackage("..adapter.out..");

	@ArchTest
	static final ArchRule outboundAdaptersDoNotDependOnInboundAdapters = noClasses()
			.that().resideInAPackage("..adapter.out..")
			.should().dependOnClassesThat().resideInAPackage("..adapter.in..");

}
