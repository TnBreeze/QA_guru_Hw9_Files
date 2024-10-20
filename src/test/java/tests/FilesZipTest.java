package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FilesZipTest {
    ClassLoader cl = FilesZipTest.class.getClassLoader();


    @Test
    void zipParseTest() throws Exception {
        try (InputStream resource = cl.getResourceAsStream("files.zip")) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    assertThat(entry.getName()).isIn("test_data.csv", "Claim.pdf", "gg.xlsx");
                }
            }
        }
    }

    @Test
    void readCsvTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("files.zip")
        ) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)
            ) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().equals("test_data.csv")) {
                        CSVReader reader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> content = reader.readAll();
                        Assertions.assertEquals(2, content.size());
                        Assertions.assertArrayEquals(
                                new String[]{"ma", " Maths"},
                                content.get(0)
                        );
                        Assertions.assertArrayEquals(
                                new String[]{"phy", " Physics"},
                                content.get(1)
                        );

                    }
                }
            }
        }
    }

    @Test
    void readXlsxTest() throws Exception {
        try (InputStream resource = cl.getResourceAsStream("files.zip")) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().equals("gg.xlsx")) {
                        XLS content = new XLS(zis);
                        assertThat(content.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).contains("Чичиков");
                        assertThat(content.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).contains("Настасья");
                    }
                }
            }
        }
    }

    @Test
    void readPdfTest() throws Exception {
        try (InputStream resource = cl.getResourceAsStream("files.zip")) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().equals("Claim.pdf")) {
                        PDF content = new PDF(zis);
                        assertThat(content.text).contains("Куда пришли дети");
                    }
                }
            }
        }
    }
}
