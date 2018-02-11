package com.apacheJena;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.sparql.vocabulary.FOAF;

public class PatientOntology extends FOAF {

    public enum OTClass {

        Compound,
        Conformer,
        Dataset,
        DataEntry,
        Feature,
        FeatureValue,
        Algorithm,
        Model,
        Validation,
        ValidationInfo;

        public String getNS() {
            System.out.println("String.format(_NS, toString()) : " + String.format(NS, toString()));
            return String.format(NS, toString());
        }

        public OntClass getOntClass(OntModel model) {
            return model.getOntClass(getNS());
        }

        public OntClass createOntClass(OntModel model) {
            return model.createClass(getNS());
        }

        public Property createProperty(OntModel model) {
            return model.createProperty(getNS());
        }
    };

    private static final Model M_MODEL = ModelFactory.createDefaultModel();
    public static final String NS = "https://en.wikipedia.org/wiki/Patient";

    public static Property idPerson;
    public static Property height;
    public static Property heartRate;
    public static Property isMoving;
    public static Property bodyTemperature;
    public static Property camera;
    public static Property medicalCondition;


    public PatientOntology() {
    }

    public static String getURI() {
        return "https://www.w3.org/TR/vocab-ssn/#SOSASensor";
    }

    static {
        idPerson = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Device");

        heartRate = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Heart_rate");
        height = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Human_height");
        isMoving = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Movement");
        bodyTemperature = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Human_body_temperature");
        camera = M_MODEL.createProperty("https://en.wikipedia.org/wiki/Hospital");

       /* medicalCondition.addProperty(heartRate, "heartRate");
        medicalCondition.addProperty(bodyTemperature,"bodyTEmperature");*/


    }
}
