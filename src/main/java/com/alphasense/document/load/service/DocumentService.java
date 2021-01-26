package com.alphasense.document.load.service;

import com.alphasense.document.load.constants.Constants;
import com.alphasense.document.load.constants.ErrorCode;
import com.alphasense.document.load.constants.LoggerConstants;
import com.alphasense.document.load.dto.DocumentDTO;
import com.alphasense.document.load.dto.Error;
import com.alphasense.document.load.exception.ApiException;
import com.alphasense.document.load.model.Document;
import com.alphasense.document.load.repository.DocumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class is holds all the business logic to save and retrieve document based on the input parameters.
 */
@Service
public class DocumentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;

    private final ObjectMapper objectMapper;

    public DocumentService(DocumentRepository documentRepository, ObjectMapper objectMapper) {
        this.documentRepository = documentRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * This method is used to retrieve all the documents from the file.
     *
     * @return - List of documents present.
     */
    public List<DocumentDTO> retrieveAllDocuments() {
        return getDocumentDTOList();
    }

    /**
     * This method is used to retrieve document based on document Id.
     *
     * @param documentId - represents the document Id.
     * @return - returns  matching document record.
     */
    public Optional<DocumentDTO> retrieveDocumentById(String documentId) {
        List<DocumentDTO> documentDTOList = getDocumentDTOList();
        return Optional.of(documentDTOList.stream().filter(documentDTO -> documentDTO.getDocumentId().equals(documentId))
                .findFirst().orElseThrow(() -> {
                            List<Error> errors = new ArrayList<>();
                            Error error = new Error(ErrorCode.BAD_REQUEST, LoggerConstants.NO_ENTITY_FOUND_FOR + documentId, LoggerConstants.NO_ENTITY_FOUND_FOR + documentId, true, Constants.DOCUMENT_SERVICE_API);
                            errors.add(error);
                            return new ApiException(errors, HttpStatus.BAD_REQUEST);
                        }
                ));
    }

    /**
     * This method is used to filter the documents based on the search parameter passed as an input.
     *
     * @param companyName  -  represents the companyName.
     * @param reporterName - represents the reporterName.
     * @param documentIds  - represents the documentIds.
     * @return - returns list of matching document records.
     */
    public List<DocumentDTO> retrieveDocumentBySearchParam(String companyName, String reporterName, List<String> documentIds) {
        List<DocumentDTO> documentDTOList = getDocumentDTOList();
        Predicate<DocumentDTO> company = documentDTO -> documentDTO.getCompanyName().equalsIgnoreCase(companyName);
        Predicate<DocumentDTO> reporter = documentDTO -> documentDTO.getReporterName().equalsIgnoreCase(reporterName);
        boolean isEmpty = Optional.ofNullable(documentIds).isPresent();
        List<String> docIds = (!isEmpty) ? new ArrayList<>() : documentIds;
        Predicate<DocumentDTO> docs = documentDTO ->
                docIds.stream().
                        anyMatch(docId -> documentDTO.getDocumentId().equals(docId));

        return documentDTOList.stream().filter(company.or(reporter).or(docs)).collect(Collectors.toList());

    }

    /**
     * This method is used to save the document object.
     *
     * @param documentDTO       - represents the input document object.
     * @param isDuplicateRecord -  represents true if document object is duplicate.
     */
    //@Async("asyncExecutor")
    public void saveDocument(DocumentDTO documentDTO, boolean isDuplicateRecord) {
        LOGGER.info(LoggerConstants.SAVING_FAILED_DOCUMENT_TO_DB + documentDTO);
        Document document = new Document();
        document.setDocumentId(documentDTO.getDocumentId());
        document.setDocumentName(documentDTO.getDocumentName());
        document.setCompanyName(documentDTO.getCompanyName());
        document.setReporterName(documentDTO.getReporterName());
        document.setDuplicate(isDuplicateRecord);
        document.setCreatedBy(Constants.DOCUMENT_LOAD_USER);
        document.setCreatedDate(new Date());
        documentRepository.save(document);
    }

    /**
     * This method is used to read the json file contents and convert it to json object.
     *
     * @return - All the documents records available in the file.
     */
    private List<DocumentDTO> getDocumentDTOList() {
        List<DocumentDTO> documentDTOList;
        try {
            documentDTOList = Arrays.asList(objectMapper.readValue(Paths.get(Constants.OUTPUT_FILE_DIRECTORY + Constants.OUTPUT_DOCUMENT_JSON).toFile(), DocumentDTO[].class));
        } catch (IOException e) {
            List<Error> errors = new ArrayList<>();
            Error error = new Error(ErrorCode.SERVER_ERROR, LoggerConstants.READING_THE_DOCUMENT_JSON_FILE, LoggerConstants.READING_THE_DOCUMENT_JSON_FILE, false, Constants.DOCUMENT_SERVICE_API);
            errors.add(error);
            throw new ApiException(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return documentDTOList;
    }
}
