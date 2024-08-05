import { useQuery } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { getParticipatedRoomList } from "@/apis/rooms.api";

const useFetchParticipatedRoomList = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST],
    queryFn: getParticipatedRoomList,
  });
};

export default useFetchParticipatedRoomList;
