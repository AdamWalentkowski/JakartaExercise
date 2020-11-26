package com.eti.pg.converter;

import com.eti.pg.board.entity.Board;
import com.eti.pg.task.view.TaskEdit;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "boardConverter")
public class BoardConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        ValueExpression vex =
                facesContext.getApplication().getExpressionFactory()
                        .createValueExpression(facesContext.getELContext(),
                                "#{taskEdit}", TaskEdit.class);
        var taskEdit = (TaskEdit)vex.getValue(facesContext.getELContext());
        return taskEdit.getBoard(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Board)o).getId().toString();
    }
}
