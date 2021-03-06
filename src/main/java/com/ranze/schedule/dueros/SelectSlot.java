package com.ranze.schedule.dueros;

import com.baidu.dueros.data.response.directive.DialogDirective;
import com.baidu.dueros.nlu.Intent;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonTypeName("Dialog.SelectSlot")
public class SelectSlot extends DialogDirective {
    private String slotToSelect;
    private List<Option> options;

    public SelectSlot() {

    }

    public SelectSlot(final String slotToSelect) {
        this.slotToSelect = slotToSelect;
    }

    public SelectSlot(Intent updatedIntent, String slotToSelect) {
        super(updatedIntent);
        this.slotToSelect = slotToSelect;
    }

    public String getSlotToSelect() {
        return slotToSelect;
    }

    public void setSlotToSelect(String slotToSelect) {
        this.slotToSelect = slotToSelect;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Getter
    @Setter
    public static class Option {
        private String type = "KEYWORD";
        private String value;
        private int index;

    }
}
