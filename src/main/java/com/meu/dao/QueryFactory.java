package com.meu.dao;

/**
 *
 * @author Éder Mello
 */
public final class QueryFactory {

    protected static String consulta;

    public static String pdfSelect() {
        return consulta = "select max(CODIGO_DOC) from dig_doc";
    }

    public static String pdfSelectText() {
        return consulta = "select * from DIG_DOCINDICE where CODIGO_DOC_IND = (select max(CODIGO_DOC_IND) from DIG_DOCINDICE)";
    }

    public static String boxCodigo_cax_doc() {
        return consulta = "select CODIGO_CAX, NOME_CAX from DIG_CAIXA";
    }

    public static String boxCodigo_cls_doc() {
        return consulta = "select CODIGO_CLS, ASSUNTO_CLS from DIG_CLASSIFICACAO";
    }

    public static String boxCodigo_tipo_doc() {
        
        return consulta = "select CODIGO_TPO, NOME_TPO from DIG_TIPODOC";
    }

    public static String maxCodDoc() {
        return consulta = "select max(CODIGO_DOC) from dig_doc";
    }
    
    public static String pdfDigDoc() {//           1            2           3               4               5               6           7           8           9               10              11              12              13              14                   15           16            17             18           19          
        return consulta = "INSERT INTO DIG_DOC (EMPRESA_DOC, CODIGO_DOC, EXERCICIO_DOC, CODIGO_CLS_DOC, CODIGO_TPO_DOC, NUMERO_DOC, DATA_DOC, RESUMO_DOC, CODIGO_LOC_DOC, CODIGO_CAX_DOC, CODIGO_PST_DOC, CODIGO_ARM_DOC, CODIGO_CNT_DOC, DATA_APROVACAO_DOC, PUBLICAVEL_DOC, LOG_INC_DOC, LOG_ALT_DOC, DTHR_INC_DOC, DTHR_ALT_DOC) "
                + "VALUES (1, ?, ?, ?, ?, NULL, ?, ?, NULL, ?, ?, NULL, NULL, NULL, ?, NULL, NULL, NULL, NULL)";
    }//                    1                    2                       3   4  5    6    7  8   9   10 11   12    13   14   15   16    17     18   19    

    public static String pdf() {//                       1            2               3            4           5            6           7           8           9               10          11              12          13              14          15          16              17              18          
        return consulta = "INSERT INTO DIG_DOCINDICE (EMPRESA_IND, CODIGO_DOC_IND, ITEM_IND, CODIGO_TPO_IND, NUMERO_IND, DATA_IND, TOTALPG_IND, TAMANHO_IND, EXTENSAO_IND, CHAVE_IND, TEXTO_OCR_IND, TEXTO_RTF_IND, LOG_INC_IND, LOG_ALT_IND, DTHR_INC_IND, DTHR_ALT_IND, COD_USR_IND, COMPLEMENTO_IND)\n"
                + " VALUES (1, (select max(CODIGO_DOC) from dig_doc), 1, ?, NULL, ?, ?, ?  , ?    , ?, ?, NULL, ? , NULL, NULL, NULL, NULL, NULL)";
    }                     //1                       2                 3  4   5   6   7   8     9    10 11 12   13     14    15     16    17   18
}
