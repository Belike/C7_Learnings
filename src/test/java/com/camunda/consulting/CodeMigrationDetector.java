package com.camunda.consulting;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.camunda.community.migration.detector.rules.Camunda7MigrationRules;
import org.camunda.community.migration.detector.rules.CodeMigrationReport;
import org.camunda.community.migration.detector.rules.CodeMigrationReportBuilder;
import org.camunda.community.migration.detector.rules.CodeMigrationReportPrinter;

import java.io.StringWriter;

@AnalyzeClasses(packages = "com.camunda.training", importOptions = ImportOption.DoNotIncludeTests.class)
public class CodeMigrationDetector {

    @ArchTest
    public void testNoImplementationOfCamunda7Interfaces(JavaClasses classes) {
        Camunda7MigrationRules.ensureNoImplementationOfCamunda7Interfaces().check(classes);
    }

    @ArchTest
    public void testNoSpringBootEvents(JavaClasses classes) {
        Camunda7MigrationRules.ensureNoSpringBootEvents().check(classes);
    }
    @ArchTest
    public void testNoInvocationOfCamunda7Api(JavaClasses classes) {
        Camunda7MigrationRules.ensureNoInvocationOfCamunda7Api().check(classes);
    }

    @ArchTest
    public void createReport(JavaClasses classes) {
        CodeMigrationReport report =
                new CodeMigrationReportBuilder(classes)
                        .withArchRule(Camunda7MigrationRules.ensureNoImplementationOfCamunda7Interfaces())
                        .withArchRule(Camunda7MigrationRules.ensureNoInvocationOfCamunda7Api())
                        .withArchRule(Camunda7MigrationRules.ensureNoSpringBootEvents())
                        .build();
        StringWriter writer = new StringWriter();
        CodeMigrationReportPrinter.print(writer, report);
    }
}
