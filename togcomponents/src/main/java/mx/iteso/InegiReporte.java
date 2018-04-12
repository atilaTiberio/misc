package mx.iteso;

import com.fasterxml.jackson.annotation.JsonProperty;


public class InegiReporte {

    @JsonProperty("CurrentValue")
    Double currentValue;

    @JsonProperty("DescriptionPeriod")
    String descriptionPeriod;

    @JsonProperty("NotesPeriod")
    String notesPeriod;

    @JsonProperty("SourcesPeriod")
    String sourcesPeriod;

    @JsonProperty("TimePeriod")
    Integer timePeriod;

    @JsonProperty("ValueStatus")
    String valueStatus;

    public InegiReporte() {
    }

    public InegiReporte(Double currentValue, String descriptionPeriod, String notesPeriod, String sourcesPeriod, Integer timePeriod, String valueStatus) {
        this.currentValue = currentValue;
        this.descriptionPeriod = descriptionPeriod;
        this.notesPeriod = notesPeriod;
        this.sourcesPeriod = sourcesPeriod;
        this.timePeriod = timePeriod;
        this.valueStatus = valueStatus;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public String getDescriptionPeriod() {
        return descriptionPeriod;
    }

    public void setDescriptionPeriod(String descriptionPeriod) {
        this.descriptionPeriod = descriptionPeriod;
    }

    public String getNotesPeriod() {
        return notesPeriod;
    }

    public void setNotesPeriod(String notesPeriod) {
        this.notesPeriod = notesPeriod;
    }

    public String getSourcesPeriod() {
        return sourcesPeriod;
    }

    public void setSourcesPeriod(String sourcesPeriod) {
        this.sourcesPeriod = sourcesPeriod;
    }

    public Integer getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(Integer timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getValueStatus() {
        return valueStatus;
    }

    public void setValueStatus(String valueStatus) {
        this.valueStatus = valueStatus;
    }
}