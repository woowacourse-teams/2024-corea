import { useFetchProfile } from "@/hooks/queries/useFetchProfile";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import Loading from "@/components/common/loading/Loading";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
import ProfileCard from "@/components/profile/profileCard/ProfileCard";
import UserParticipatedRoom from "@/components/profile/userParticipatedRoom/UserParticipatedRoom";
import * as S from "@/pages/profile/ProfilePage.style";

const ProfilePage = () => {
  const { data: profileData } = useFetchProfile();

  return (
    <S.ProfilePageContainer>
      <ProfileCard {...profileData} />
      <UserParticipatedRoom />
    </S.ProfilePageContainer>
  );
};

export default WithSuspense(
  ProfilePage,
  <DelaySuspense>
    <Loading />
  </DelaySuspense>,
);
