import { useFetchProfile } from "@/hooks/queries/useFetchProfile";
import ContentSection from "@/components/common/contentSection/ContentSection";
import ProfileCard from "@/components/profile/profileCard/ProfileCard";
import UserParticipatedRoom from "@/components/profile/userParticipatedRoom/UserParticipatedRoom";
import * as S from "@/pages/profile/ProfilePage.style";

const ProfilePage = () => {
  const { data: profileData } = useFetchProfile();

  return (
    <S.ProfilePageContainer>
      <ContentSection title="프로필">
        <ProfileCard {...profileData} />
      </ContentSection>

      <UserParticipatedRoom />
    </S.ProfilePageContainer>
  );
};

export default ProfilePage;
