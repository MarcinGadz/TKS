//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.07 at 06:27:29 PM CEST 
//


package com.edu.tks.model.record;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.edu.tks.model.record package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.edu.tks.model.record
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRecordsRequest }
     * 
     */
    public GetRecordsRequest createGetRecordsRequest() {
        return new GetRecordsRequest();
    }

    /**
     * Create an instance of {@link GetRecordsResponse }
     * 
     */
    public GetRecordsResponse createGetRecordsResponse() {
        return new GetRecordsResponse();
    }

    /**
     * Create an instance of {@link RecordSOAP }
     * 
     */
    public RecordSOAP createRecordSOAP() {
        return new RecordSOAP();
    }

    /**
     * Create an instance of {@link GetRecordByIdRequest }
     * 
     */
    public GetRecordByIdRequest createGetRecordByIdRequest() {
        return new GetRecordByIdRequest();
    }

    /**
     * Create an instance of {@link GetRecordByIdResponse }
     * 
     */
    public GetRecordByIdResponse createGetRecordByIdResponse() {
        return new GetRecordByIdResponse();
    }

    /**
     * Create an instance of {@link AddRecordRequest }
     * 
     */
    public AddRecordRequest createAddRecordRequest() {
        return new AddRecordRequest();
    }

    /**
     * Create an instance of {@link AddRecordResponse }
     * 
     */
    public AddRecordResponse createAddRecordResponse() {
        return new AddRecordResponse();
    }

    /**
     * Create an instance of {@link RemoveRecordRequest }
     * 
     */
    public RemoveRecordRequest createRemoveRecordRequest() {
        return new RemoveRecordRequest();
    }

    /**
     * Create an instance of {@link RemoveRecordResponse }
     * 
     */
    public RemoveRecordResponse createRemoveRecordResponse() {
        return new RemoveRecordResponse();
    }

    /**
     * Create an instance of {@link ModifyRecordRequest }
     * 
     */
    public ModifyRecordRequest createModifyRecordRequest() {
        return new ModifyRecordRequest();
    }

    /**
     * Create an instance of {@link ModifyRecordResponse }
     * 
     */
    public ModifyRecordResponse createModifyRecordResponse() {
        return new ModifyRecordResponse();
    }

}
