package model;

public enum GameStatusEnum {
     NON_STARTED("NÃO INICIADO"),
     INCOMPLETE("INCOMPLETO"),
    COMPLETE("COMPLETO");

     private String label;

     GameStatusEnum(final String label) {
         this.label = label;
     }

    public String getLabel() {
        return label;
    }
}
