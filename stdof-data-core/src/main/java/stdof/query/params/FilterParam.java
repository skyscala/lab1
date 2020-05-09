/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.params;

/**
 *
 * @author zeyarh
 */
public class FilterParam {

    private int order;
    private String key;
    private String filterType;
    private String filterExpression;
    private TextValue textValue;
    private TextArrayValue textArrayValue;
    private DateValue dateValue;
    private DateRangeValue dateRangeValue;
    private NumberValue numberValue;
    private Object objectValue;

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the filterType
     */
    public String getFilterType() {
        return filterType;
    }

    /**
     * @param filterType the filterType to set
     */
    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    /**
     * @return the filterExpression
     */
    public String getFilterExpression() {
        return filterExpression;
    }

    /**
     * @param filterExpression the filterExpression to set
     */
    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    /**
     * @return the textValue
     */
    public TextValue getTextValue() {
        return textValue;
    }

    /**
     * @param textValue the textValue to set
     */
    public void setTextValue(TextValue textValue) {
        this.textValue = textValue;
    }

    /**
     * @return the textArrayValue
     */
    public TextArrayValue getTextArrayValue() {
        return textArrayValue;
    }

    /**
     * @param textArrayValue the textArrayValue to set
     */
    public void setTextArrayValue(TextArrayValue textArrayValue) {
        this.textArrayValue = textArrayValue;
    }

    /**
     * @return the dateValue
     */
    public DateValue getDateValue() {
        return dateValue;
    }

    /**
     * @param dateValue the dateValue to set
     */
    public void setDateValue(DateValue dateValue) {
        this.dateValue = dateValue;
    }

    /**
     * @return the dateRangeValue
     */
    public DateRangeValue getDateRangeValue() {
        return dateRangeValue;
    }

    /**
     * @param dateRangeValue the dateRangeValue to set
     */
    public void setDateRangeValue(DateRangeValue dateRangeValue) {
        this.dateRangeValue = dateRangeValue;
    }

    /**
     * @return the numberValue
     */
    public NumberValue getNumberValue() {
        return numberValue;
    }

    /**
     * @param numberValue the numberValue to set
     */
    public void setNumberValue(NumberValue numberValue) {
        this.numberValue = numberValue;
    }

    /**
     * @return the objectValue
     */
    public Object getObjectValue() {
        return objectValue;
    }

    /**
     * @param objectValue the objectValue to set
     */
    public void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
    }
    
    
}
