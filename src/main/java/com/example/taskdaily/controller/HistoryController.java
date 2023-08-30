package com.example.taskdaily.controller;

import com.example.taskdaily.domain.TaskHistory;
import com.example.taskdaily.service.Task.TaskService;
import com.example.taskdaily.service.TaskHistory.TaskHistoryService;
import com.example.taskdaily.service.TaskHistory.request.TaskSaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value ="/view")
@AllArgsConstructor
public class HistoryController {
    private final TaskHistoryService taskHistoryService;
    @GetMapping("")
    public ModelAndView showListView(@RequestParam(required = false) String message) {
        ModelAndView view = new ModelAndView("/history/list");
        view.addObject("view", taskHistoryService.getListView());
        view.addObject("message", message);
        return view;
    }
    @GetMapping("/date")
    public ModelAndView showListDate(@RequestParam(name = "localDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate){
        ModelAndView view = new ModelAndView("/history/list");
        view.addObject("view", taskHistoryService.getListDate(localDate));

        return view;
    }
    @GetMapping("/thongke")
    public ModelAndView showStatusTotals(@RequestParam("inputDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inputDate) {
        ModelAndView view = new ModelAndView("/history/thongke");

        List<Object[]> statusCounts = taskHistoryService.getStatusCounts(inputDate);
        Map<String, Long> statusTotals = taskHistoryService.calculateStatusTotals(statusCounts);

        view.addObject("statusTotals", statusTotals);
        view.addObject("inputDate", inputDate);
        return view;
    }
    @GetMapping("/thongke-week")
    public ModelAndView showStatusTotalsByWeek(@RequestParam("inputWeek") String inputWeek) {
        ModelAndView view = new ModelAndView("/history/thongke");

        String[] parts = inputWeek.split("-W");
        int year = Integer.parseInt(parts[0]);
        int weekNumber = Integer.parseInt(parts[1]);

        LocalDate startOfWeek = LocalDate.ofYearDay(year, 1)
                .with(TemporalAdjusters.firstDayOfYear())
                .plus(Period.ofWeeks(weekNumber))
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalDate endOfWeek = startOfWeek.plusDays(6); // Kết thúc của tuần là 6 ngày sau ngày bắt đầu

        List<Object[]> statusCounts = taskHistoryService.getStatusCountsByWeek(startOfWeek, endOfWeek);
        Map<String, Long> statusTotals = taskHistoryService.calculateStatusTotals(statusCounts);

        view.addObject("statusTotals", statusTotals);
        view.addObject("inputWeek", inputWeek); // Truyền tuần nhập vào để hiển thị trên giao diện

        return view;
    }
    @GetMapping("/thongke-month")
    public ModelAndView showStatusTotalsByMonth(@RequestParam("inputMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth inputMonth) {
        ModelAndView view = new ModelAndView("history/thongke");
        LocalDate startOfMonth = inputMonth.atDay(1);
        LocalDate endOfMonth = inputMonth.atEndOfMonth();
        List<Object[]> statusCounts = taskHistoryService.getStatusCountsByWeek(startOfMonth, endOfMonth);
        Map<String, Long> statusTotals = taskHistoryService.calculateStatusTotals(statusCounts);
        view.addObject("statusTotals", statusTotals);
        view.addObject("inputMonth", inputMonth);
        return view;
    }
}


