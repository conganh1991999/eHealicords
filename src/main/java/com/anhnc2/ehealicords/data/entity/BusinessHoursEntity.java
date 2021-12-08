package com.anhnc2.ehealicords.data.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business_hours")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BusinessHoursEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalTime morningOpen;

    private LocalTime morningClose;

    private LocalTime afternoonOpen;

    private LocalTime afternoonClose;

    private LocalTime eveningOpen;

    private LocalTime eveningClose;

    private String days;

    @JsonGetter("days")
    @Enumerated(EnumType.ORDINAL)
    public List<Integer> getListOfDays() {
        if (this.days == null || this.days.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.days.split(","))
                .map(day -> DayOfWeek.valueOf(day).getValue())
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public boolean isHasMorningShift(){
        return this.morningOpen != null && this.morningClose != null;
    }

    @JsonIgnore
    public boolean isHasAfternoonShift(){
        return this.afternoonOpen != null && this.afternoonClose != null;
    }

    @JsonIgnore
    public boolean isHasEveningShift(){
        return this.eveningOpen != null && this.eveningClose != null;
    }
}
