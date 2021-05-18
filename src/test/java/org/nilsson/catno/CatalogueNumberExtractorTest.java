package org.nilsson.catno;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CatalogueNumberExtractorTest {

    @Test
    public void shouldFindCatalogueNumber() {
        String expected = "E 048-35144";

        String catalogueNumber = CatalogueNumberExtractor.extractCatalogueNumber("LETTER\n" +
                "4E 048-35144\n" +
                "SO\n" +
                "\n" +
                "ZARAH LEANDER rv\n" +
                "mod Einar Groths orkestafi.T-3\n" +
                "mod Arno Hulphers”öfkostar: 4, 5\n" +
                "\n" +
                "fa\n" +
                "jary - Runo Moborg)\n" +
                "\n" +
                "RT rad felt AA SN [Kr\n" +
                "Maborg). 4. KVINNOR, KVINNOR, KVINNOR, KVINNOR\n" +
                "[STATE EN TEESE SDL VALT\n" +
                "LUTNING RaED\n" +
                "Gösta Rybranl).\n" +
                "\n" +
                " \n");

        assertThat(catalogueNumber).containsPattern("[A-Z]?" + expected + "[A-Z]?");

    }

    @Test
    public void shouldPickCorrectCatalogueNumber() {
        String expected = "31058 I";
        String catalogueNumber = CatalogueNumberExtractor.extractCatalogueNumber("Sa\n" +
                "M33\n" +
                "\n" +
                "vV\n" +
                "KATALOG-NR.\n" +
                "31058 I");

        assertThat(catalogueNumber).isEqualTo(expected);

    }
}