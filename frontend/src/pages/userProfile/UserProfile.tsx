import { useNavigate, useParams } from "react-router-dom";
import { useFetchUserProfile } from "@/hooks/queries/useFetchProfile";
import ProfileCard from "@/components/profile/profileCard/ProfileCard";
import * as S from "@/pages/profile/ProfilePage.style";

const UserProfile = () => {
  const params = useParams();
  const userName = params.username;
  const navigate = useNavigate();

  if (!userName) {
    navigate(-1);
    return;
  }

  const { data: profileData } = useFetchUserProfile(userName);

  return (
    <S.ProfilePageContainer>
      <ProfileCard {...profileData} />
    </S.ProfilePageContainer>
  );
};

export default UserProfile;
