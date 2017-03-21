package com.lewis.easyui.widget.timessquare;

import java.util.Date;

public interface CalendarCellDecorator {
  void decorate(CalendarCellView cellView, Date date, boolean selectable);
}
