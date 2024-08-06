package corea.room.controller;

import corea.auth.domain.AuthInfo;
import corea.matching.dto.MatchResultResponses;
import corea.room.dto.RoomResponse;
import corea.room.dto.RoomResponses;
import org.springframework.http.ResponseEntity;

public interface RoomControllerSpecification {

    ResponseEntity<RoomResponse> room(long id, AuthInfo authInfo);

    ResponseEntity<RoomResponses> participatedRooms(AuthInfo authInfo);

    ResponseEntity<RoomResponses> openedRooms(AuthInfo authInfo, String expression, int page);

    ResponseEntity<MatchResultResponses> reviewers(long id, AuthInfo authInfo);

    ResponseEntity<MatchResultResponses> reviewees(long id, AuthInfo authInfo);
}
