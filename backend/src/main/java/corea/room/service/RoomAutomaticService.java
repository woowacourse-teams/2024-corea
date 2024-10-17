package corea.room.service;

import corea.room.domain.Room;
import corea.scheduler.domain.*;
import corea.scheduler.service.AutomaticMatchingScheduler;
import corea.scheduler.service.AutomaticUpdateScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomAutomaticService {

    private final AutomaticUpdateWriter automaticUpdateWriter;
    private final AutomaticUpdateReader automaticUpdateReader;

    private final AutomaticMatchingWriter automaticMatchingWriter;
    private final AutomaticMatchingReader automaticMatchingReader;

    private final AutomaticMatchingScheduler automaticMatchingScheduler;
    private final AutomaticUpdateScheduler automaticUpdateScheduler;

    @Transactional
    public void updateTime(Room updateRoom) {
        AutomaticMatching automaticMatching = automaticMatchingReader.findWithRoom(updateRoom);
        AutomaticUpdate automaticUpdate = automaticUpdateReader.findWithRoom(updateRoom);

        automaticMatchingWriter.updateTime(automaticMatching, updateRoom.getRecruitmentDeadline());
        automaticUpdateWriter.updateTime(automaticUpdate, updateRoom.getReviewDeadline());

        automaticMatchingScheduler.modifyTask(updateRoom);
        automaticUpdateScheduler.modifyTask(updateRoom);
    }

    @Transactional
    public void createAutomatic(Room room) {
        automaticMatchingWriter.create(room);
        automaticUpdateWriter.create(room);

        automaticMatchingScheduler.matchOnRecruitmentDeadline(room);
        automaticUpdateScheduler.updateAtReviewDeadline(room);
    }

    @Transactional
    public void deleteAutomatic(Room room) {
        AutomaticMatching automaticMatching = automaticMatchingReader.findWithRoom(room);
        AutomaticUpdate automaticUpdate = automaticUpdateReader.findWithRoom(room);

        automaticMatchingWriter.delete(automaticMatching);
        automaticUpdateWriter.delete(automaticUpdate);

        automaticMatchingScheduler.cancel(room.getId());
        automaticUpdateScheduler.cancel(room.getId());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void schedulePendingAutomaticMatching() {
        List<AutomaticMatching> matchings = automaticMatchingReader.findAllByStatus(ScheduleStatus.PENDING);

        log.info("{}개의 방에 대해 자동 매칭 재예약 시작", matchings.size());

        matchings.forEach(automaticMatchingScheduler::matchOnRecruitmentDeadline);

        log.info("{}개의 방에 대해 자동 매칭 재예약 완료", matchings.size());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void schedulePendingAutomaticUpdate() {
        List<AutomaticUpdate> updates = automaticUpdateReader.findAllByStatus(ScheduleStatus.PENDING);

        log.info("{}개의 방에 대해 자동 상태 업데이트 재예약 시작", updates.size());

        updates.forEach(automaticUpdateScheduler::updateAtReviewDeadline);

        log.info("{}개의 방에 대해 자동 상태 업데이트 재예약 완료", updates.size());
    }
}
