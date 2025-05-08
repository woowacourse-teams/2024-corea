import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useFetchUserProfile } from "@/hooks/queries/useFetchProfile";
import ProfileCard from "@/components/profile/profileCard/ProfileCard";
import * as S from "@/pages/profile/ProfilePage.style";

const UserProfilePage = () => {
  const params = useParams();
  const userName = params.username;
  const navigate = useNavigate();

  useEffect(() => {
    if (!userName) {
      navigate(-1);
    }
  }, [userName, navigate]);

  const { data: profileData } = useFetchUserProfile(userName || "");

  return (
    <S.ProfilePageContainer>
      {profileData && <ProfileCard {...profileData} />}
    </S.ProfilePageContainer>
  );
};

export default UserProfilePage;
