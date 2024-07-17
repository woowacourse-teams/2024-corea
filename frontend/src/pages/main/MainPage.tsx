import { useEffect, useState } from "react";
import ContentForm from "@/components/common/contentForm/ContentForm";
import RoomList from "@/components/shared/roomList/RoomList";
import * as S from "@/pages/main/MainPage.style";
import { RoomInfo } from "@/@types/roomInfo";
import { getRoomList } from "@/apis/rooms.api";

const MainPage = () => {
  const [roomList, setRoomList] = useState<{ rooms: RoomInfo[] }>();

  const fetchRoomListData = async () => {
    const res = await getRoomList();
    setRoomList(res);
  };

  useEffect(() => {
    fetchRoomListData();
  }, []);

  return (
    <S.Layout>
      <ContentForm title="참여 중인 방">
        {roomList && <RoomList roomList={roomList.rooms} />}
      </ContentForm>
    </S.Layout>
  );
};

export default MainPage;
