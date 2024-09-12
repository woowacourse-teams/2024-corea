import * as S from "./ProfilePage.style";
import { useFetchProfile } from "@/hooks/queries/useFetchProfile";
import ProfileCard from "@/components/profile/profileCard/ProfileCard";
import UserParticipatedRoom from "@/components/profile/userParticipatedRoom/UserParticipatedRoom";

const ProfilePage = () => {
  const { data: profileData } = useFetchProfile();

  return (
    <S.ProfilePageContainer>
      <ProfileCard {...profileData} />
      <UserParticipatedRoom />
    </S.ProfilePageContainer>
  );
};

export default ProfilePage;
