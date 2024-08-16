import * as S from "./ProfilePage.style";
import { useFetchProfile } from "@/hooks/queries/useFetchProfile";
import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import ProfileCard from "@/components/profile/profileCard/ProfileCard";
import RoomList from "@/components/shared/roomList/RoomList";

const ProfilePage = () => {
  const { data: profileData } = useFetchProfile();
  const { data: roomList } = useFetchParticipatedRoomList();

  const participatingRoomList = roomList.rooms.filter((room) => !room.isClosed);
  const participatedRoomList = roomList.rooms.filter((room) => room.isClosed);

  return (
    <S.ProfilePageContainer>
      <ProfileCard {...profileData} />
      <ContentSection title="참여 중인 방 리스트">
        <RoomList roomList={participatingRoomList} roomType="participated" />
      </ContentSection>
      <ContentSection title="참여 했던 방 리스트">
        <RoomList roomList={participatedRoomList} roomType="participated" />
      </ContentSection>
    </S.ProfilePageContainer>
  );
};

export default ProfilePage;
