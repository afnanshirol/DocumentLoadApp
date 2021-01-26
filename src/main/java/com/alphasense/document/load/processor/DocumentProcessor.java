package com.alphasense.document.load.processor;

import com.alphasense.document.load.constants.LoggerConstants;
import com.alphasense.document.load.dto.DocumentDTO;
import com.alphasense.document.load.service.DocumentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * This class is used to process the document records.
 * It also validates the document object and saves the dirty object to database.
 */
@Component
public class DocumentProcessor implements ItemProcessor<DocumentDTO, DocumentDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentProcessor.class);

    public final HashSet<DocumentDTO> documentDTOS = new HashSet<>();

    private final DocumentService documentService;

    public DocumentProcessor(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public DocumentDTO process(DocumentDTO documentDTO) {
        boolean isDocumentIdPresent = StringUtils.isEmpty(documentDTO.getDocumentId());
        boolean isDocumentNamePresent = StringUtils.isEmpty(documentDTO.getDocumentName());

        boolean isDuplicateRecord = documentDTOS.contains(documentDTO);

        if (isDocumentIdPresent | isDocumentNamePresent | isDuplicateRecord) {
            LOGGER.info(LoggerConstants.INVALID_DOCUMENT_DTO_RECORD_FOUND + documentDTO);
            documentService.saveDocument(documentDTO,isDuplicateRecord);
            return null;
        }
        documentDTOS.add(documentDTO);
        return documentDTO;
    }

}
