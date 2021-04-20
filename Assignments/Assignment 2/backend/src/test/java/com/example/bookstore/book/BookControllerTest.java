package com.example.bookstore.book;

import com.example.bookstore.BaseControllerTest;
import com.example.bookstore.TestCreationFactory;
import com.example.bookstore.frontoffice.controller.BookController;
import com.example.bookstore.frontoffice.model.Book;
import com.example.bookstore.frontoffice.model.dto.BookDTO;
import com.example.bookstore.frontoffice.report.CsvReportService;
import com.example.bookstore.frontoffice.report.PdfReportService;
import com.example.bookstore.frontoffice.report.ReportServiceFactory;
import com.example.bookstore.frontoffice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookstore.TestCreationFactory.*;
import static com.example.bookstore.frontoffice.controller.UrlMapping.*;
import static com.example.bookstore.frontoffice.report.ReportType.CSV;
import static com.example.bookstore.frontoffice.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CsvReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOKS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void exportReport() throws Exception {

        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOKS + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .name(randomString())
                .quantity(randomInt())
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void update() throws Exception {
        int id = randomInt();
        BookDTO reqItem = BookDTO.builder()
                .id(id)
                .name(randomString())
                .author(randomString())
                .build();

        when(bookService.update(id, reqItem)).thenReturn(reqItem);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, reqItem, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqItem));
    }

    @Test
    void delete() throws Exception {
        int id = randomInt();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk());
        verify(bookService, times(1)).delete(id);
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(bookService).deleteAll();

        ResultActions result = performDelete(BOOKS);
        result.andExpect(status().isOk());
        verify(bookService, times(1)).deleteAll();
    }

    //Not working yet
    /*
    @Test
    void sell() throws Exception {
        BookDTO book = BookDTO.builder()
                .id(randomInt())
                .name(randomString())
                .author(randomString())
                .genre(randomString())
                .quantity(5)
                .build();

        when(bookService.sell(book.getId())).thenReturn(true);

        ResultActions result = performPatchWithRequestBody(BOOKS + ENTITY, book.getId());
        result.andExpect(status().isOk());
    }
    */
    //Not working yet
    /*
    @Test
    public void findByNameOrAuthorOrGenre() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .id(randomInt())
                .author("something")
                .genre(randomString())
                .build();

        List<BookDTO> books = new ArrayList<>();
        books.add(bookDTO);

        when(bookService.findByNameOrAuthorOrGenre("%something%")).thenReturn(books);

        ResultActions response = performGetWithPathVariable(BOOKS + SPECIFIC_BOOK, "%something%");

        response.andExpect(status().isOk());
    } */
}