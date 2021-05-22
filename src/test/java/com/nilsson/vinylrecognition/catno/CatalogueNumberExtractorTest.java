package com.nilsson.vinylrecognition.catno;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogueNumberExtractorTest {

    @Test
    public void shouldFindCatalogueNumber() {
        String expected = "E 048-35144";

        String catalogueNumber = CatalogueNumberExtractor.extractCatalogueNumber("""
                                        
                TA
                           
                LTT ENEE
                                
                4E 048-35144
                CI
                                
                \s
                                
                V
                J
                                
                mod Einar Groths orkestafi.T-3
                G
                7
                                
                ZARAH LEANDER
                                
                ELR VETT RENEE
                                
                ZY TSE TASSAR TNE FETT MKT Er Tee
                                
                Is. Forlin) 2. SKEPP OHOJ (Michaol Jary = Runo Maborg]
                                
                CH SÅ DANSAR JAG EN ARDAS (Michael Jary = Rune
                                
                Maborg) 4. KVINNOR; KVINNOR, KVI INNOR, KVINNOR
                [STATE EN TEESE SDL VALT
                                
                UTTER NR TG RN
                Gösta Rybroni)

                """);

        assertThat(catalogueNumber).containsPattern("[A-Z]?" + expected + "[A-Z]?");

    }

    @Test
    public void shouldPickCorrectCatalogueNumber() {
        String expected = "31058 I";
        String catalogueNumber = CatalogueNumberExtractor.extractCatalogueNumber("""
                Sa
                M33

                vV
                KATALOG-NR.
                31058 I""");

        assertThat(catalogueNumber).isEqualTo(expected);

    }

    @Test
    public void shouldPickCorrectCatalogueNumber2() {
        String expected = "2600831";
        String catalogueNumber = CatalogueNumberExtractor.extractCatalogueNumber("""

                a rpm 2600831
                ereo (4, 6, 7, 9, 10)
                no (1-3, 5, 8)

                SIDA A
                b
                G 132 Testare HERMAN MÖLLER hälsar välkommen 2. MIN
                AVET. Tal sg. [CVert Taube) EVERT TALIAE = äre SKIMRANDE VAR ALDRIG
                AVET 141 (E ND DU VÄ LSI RAGANCIA 3.25 (Evoig ORG
                YENLREKNLTAUDE 5. LAND DU VÄLSIGNARE Alt rk
                JUSSI BJÖRLING 6. FARVÄL a > Heep. Hellberg) ZARAIN
                LEANDER 7. VISSI DARTE Puccini) BIRGIT NI
                Karl Goshor OCH KARUSELLEN 1.20. frin el GT Öst
                Kari Gerhard : Gösta Stenberg) KARL Gr 3jl/ HAGA""");

        assertThat(catalogueNumber).isEqualTo(expected);

    }

    @Disabled(value = "CatalogueNumberExtractor is not advanced enough to perfectly extract correct")
    @Test
    public void shouldPickCorrectCatalogueNumber3() {
        String expected = "PL 40164";
        String catalogueNumber = CatalogueNumberExtractor.extractCatalogueNumber("""
                Vw
                                
                sidal Stereo
                                
                Hela världen behöver ha en sång
                Halleluja | 5:
                | Ske fö L PL 40164
                1; HELA VÄRLDEN BEHUVER HA EN SÄNG
                - Moreno - Olle: Bergman)
                rr KOshrar « Olle Bergman)
                                
                Kyssande vind
                Vals till Marie ;
                Dansen går på Svinsta skär SA SN
                Gläds åt var morgon är
                f
                ES fena Keen Hjalmar Gullberg)
                                
                       
                [ISAE 25001
                4. VALS TILL MARIE.
                [Adam Mirchell = Beng! Hoslum)
                5. DANSEN OAR PÅ SVINSTA SKAR
                (Gideon Wahlberg)
                6. GLADS AT VAR MORGON
                [CarStevens + E Forieon =
                iawkey Fronzen)
                JAN MALMSJO
                Lars Samuelson med kör och orkester
                Producent; Carl.Eric Hjelm
                """);

        assertThat(catalogueNumber)
                .contains(expected)
                .hasSizeLessThan(expected.length() + 3);

    }

    @Disabled(value = "CatalogueNumberExtractor is not advanced enough to perfectly extract correct")
    @Test
    public void shouldPickCorrectCatalogueNumber4() {
        String expected = "LBLP 008";
        String catalogueNumber = CatalogueNumberExtractor.extractCatalogueNumber("""
                                
                LBLP 008 A DEM nce
                2 APM STEREO
                                
                LILL LINDFORS
                "EN LILLsk JUL"
                                
                TAI RUTALLON GÅR I DANSEN 223 (Trac. Arr. Peter Ljung)
                3 MD TUSEN JULELJUS 2.07 (E. Köhler,
                RÅDER 2.02 (W. Sofva/A. Smedberg) DF
                (Trad. Ar.
                                
                '. Peter Ljung)
                $- STILLA NATT 2.55 (F. Gruberg.
                tad. Arr. Peter Ljung)
                          
                """);

        assertThat(catalogueNumber)
                .contains(expected)
                .hasSizeLessThan(expected.length() + 3);

    }
}