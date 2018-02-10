import java.math.BigDecimal;

class Answer {

    public BigDecimal acc;
    public String rest;

    public Answer(BigDecimal v, String r)
    {
        this.acc = v;
        this.rest = r;
    }
}