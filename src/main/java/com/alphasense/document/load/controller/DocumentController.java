package com.alphasense.document.load.controller;

import com.alphasense.document.load.dto.DocumentDTO;
import com.alphasense.document.load.service.DocumentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is responsible to handle the request related to document object.
 */
@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * This method is used to retrieve all the documents from the file.
     *
     * @return - List of all documents present in the file.
     */
    @GetMapping(path = "/documents")
    public ResponseEntity<List<DocumentDTO>> retrieveAllDocuments() {
        return ResponseEntity.ok().body(documentService.retrieveAllDocuments());
    }

    /**
     * This method is used to retrieve document based on document Id.
     *
     * @param documentId - represents the document Id.
     * @return - returns matching document record.
     */
    @GetMapping(path = "/documents/{documentId}")
    public ResponseEntity<DocumentDTO> retrieveDocumentById(@PathVariable String documentId) {
        return ResponseEntity.ok().body(documentService.retrieveDocumentById(documentId).get());
    }

    /**
     * This method is used to filter the documents based on the search parameter passed as an input by the user.
     *
     * @param companyName  -  represents the companyName.
     * @param reporterName - represents the reporterName.
     * @param documentIds  - represents the documentIds.
     * @return - returns list of matching document records.
     */
    @GetMapping(path = "/documents/search")
    public ResponseEntity<List<DocumentDTO>> retrieveAllDocuments(@RequestParam(value = "documentIds", required = false) List<String> documentIds,
                                                  @RequestParam(value = "companyName", required = false) String companyName,
                                                  @RequestParam(value = "reporterName", required = false) String reporterName) {
        if (!StringUtils.isEmpty(companyName) || !StringUtils.isEmpty(reporterName) || !ObjectUtils.isEmpty(documentIds)) {
            return ResponseEntity.ok().body(documentService.retrieveDocumentBySearchParam(companyName, reporterName, documentIds));
        }
        return ResponseEntity.ok().body(documentService.retrieveAllDocuments());
    }


}
