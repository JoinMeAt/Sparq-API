CREATE DEFINER=`bacondev`@`%` PROCEDURE `GetSchedule`(
IN UserID bigint,
IN Today Date,
OUT Success bit(1),
OUT ErrorID smallint
)
BEGIN
    
    DECLARE SchoolID bigint;
    DECLARE ScheduleID bigint;
    set SchoolID = (Select FK_SchoolID from UserGradeAtSchool where FK_UserID = UserID AND Active = TRUE);
    
    IF Schedule is null 
    THEN
		SET Success = 0;
        SET ErrorID = 1;
    END IF;
    
    
    set ScheduleID = (Select PK_ScheduleID from Schedules where FK_SchoolID = SchoolID
															AND StartDate <= Today
                                                            AND StopDate >= Today);
    IF Schedule is null 
    THEN
		SET Success = 0;
        SET ErrorID = 2;
    END IF;
    
    ## Select school info
	select 
		u.PK_UserID as StudentID,
        u.Type,
        ugs.Grade,
        s.Name,
        s.TimeZone,
        sch.StartDate,
        sch.StopDate
    from Users as u
    INNER JOIN UserGradeAtSchool as ugs
    ON ugs.FK_UserID = u.PK_UserID
    AND ugs.Active = true
    INNER JOIN Schools as s
    ON s.PK_SchoolID = ugs.FK_SchoolID
    and s.Active = true
	INNER JOIN Schedules as sch
    ON sch.FK_SchoolID = s.PK_SchoolID
    WHERE u.PK_UserID = UserID
    AND sch.PK_ScheduleID = ScheduleID;
    
    ## Select Rotational Days
    select
    rd.PK_DayID,
    rd.Name
    from RotationalDays as rd
    INNER JOIN Schedules as sch
    on sch.PK_ScheduleID = rd.FK_ScheduleID
    where sch.FK_SchoolID = SchoolID
    AND sch.PK_ScheduleID = ScheduleID;
    
    ## Select Periods
    select
    PK_PeriodID,
    Number,
    Start,
    Stop
    from Periods
    where FK_ScheduleID = ScheduleID;
    
    ## Select Classes
    select
    c.PK_ClassID,
    c.Subject,
    c.Section,
    c.Room,
    u.FirstName,
    u.LastName,
    u.Email
    from ClassesForUser cu
    INNER JOIN Classes c
    ON c.PK_ClassID = cu.FK_ClassID
    INNER JOIN Users u
    on u.PK_UserID = c.FK_TeacherID
    where cu.FK_UserID = UserID
    AND c.FK_Schedule = ScheduleID;
    
    ## Select Class Links
    select
    cm.FK_ClassID,
    cm.FK_PeriodID,
    cm.FK_DayID
    from ClassesForUser cu
    INNER JOIN Classes c 
    on c.PK_ClassID = cu.FK_ClassID
    INNER JOIN ClassMeetings cm
    on cm.PK_ClassID = cm.FK_ClassID
    where c.FK_ScheduleID = ScheduleID
    and cu.FK_UserID = UserID;
    
    
    SET Success = True;
    
    
    
    
END