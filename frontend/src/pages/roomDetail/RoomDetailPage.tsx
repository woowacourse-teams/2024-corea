import { useNavigate, useParams } from "react-router-dom";
import useModal from "@/hooks/common/useModal";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import { useFetchDetailRoomInfo } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import FeedbackProcessInfo from "@/components/roomDetailPage/feedbackProcessInfo/FeedbackProcessInfo";
import MyReviewee from "@/components/roomDetailPage/myReviewee/MyReviewee";
import MyReviewer from "@/components/roomDetailPage/myReviewer/MyReviewer";
import ParticipantList from "@/components/roomDetailPage/participantList/ParticipantList";
import RoomInfoCard from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard";
import * as S from "@/pages/roomDetail/RoomDetailPage.style";
import { defaultCharacter } from "@/assets";
import MESSAGES from "@/constants/message";

const RoomDetailPage = () => {
  const params = useParams();
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();
  const roomId = params.id ? Number(params.id) : 0;
  const { data: roomInfo } = useFetchDetailRoomInfo(roomId);
  const { deleteParticipateInMutation, deleteParticipatedRoomMutation } = useMutateRoom();
  const navigate = useNavigate();

  const handleCancelParticipateInClick = () => {
    deleteParticipateInMutation.mutate(roomInfo.id, {
      onSuccess: () => navigate("/"),
    });
  };

  const handleDeleteRoomClick = () => {
    deleteParticipatedRoomMutation.mutate(roomInfo.id, {
      onSuccess: () => navigate("/"),
    });
  };

  const handleConfirm = () => {
    if (roomInfo.participationStatus === "MANAGER") {
      handleDeleteRoomClick();
      return;
    }
    handleCancelParticipateInClick();
  };

  const buttonProps =
    roomInfo.roomStatus === "OPEN" && roomInfo.participationStatus !== "NOT_PARTICIPATED"
      ? {
          button: {
            label: roomInfo.participationStatus === "MANAGER" ? "방 삭제하기" : "방 나가기",
            onClick: handleOpenModal,
          },
        }
      : {};

  if (roomInfo.participationStatus === "NOT_PARTICIPATED") {
    return (
      <S.Layout>
        <ContentSection title="미션 정보" {...buttonProps}>
          <RoomInfoCard roomInfo={roomInfo} />
        </ContentSection>

        <S.ImgWithError>
          <img src={defaultCharacter} alt="참여 중인 방이 아닙니다." />
          <p>참여 중인 방이 아닙니다.</p>
        </S.ImgWithError>
      </S.Layout>
    );
  }

  if (roomInfo.roomStatus === "FAIL") {
    return (
      <S.Layout>
        <ContentSection title="미션 정보" {...buttonProps}>
          <RoomInfoCard roomInfo={roomInfo} />
        </ContentSection>

        <S.ImgWithError>
          <img src={defaultCharacter} alt="매칭 실패시 이미지" />
          <p>{roomInfo.message}</p>
        </S.ImgWithError>
      </S.Layout>
    );
  }

  return (
    <S.Layout>
      <ConfirmModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onConfirm={handleConfirm}
        onCancel={handleCloseModal}
      >
        {roomInfo.participationStatus === "MANAGER"
          ? MESSAGES.GUIDANCE.DELETE_ROOM
          : MESSAGES.GUIDANCE.EXIT_ROOM}
      </ConfirmModal>

      <ContentSection title="미션 정보" {...buttonProps}>
        <RoomInfoCard roomInfo={roomInfo} />
      </ContentSection>

      <S.FeedbackContainer>
        <S.FeedbackSection>
          <ContentSection title="나의 리뷰어 - 나를 리뷰해주는 분">
            <S.StyledDescription>
              리뷰어 피드백은 소프트 스킬 역량에 한해서 진행합니다.
            </S.StyledDescription>
            <MyReviewer roomInfo={roomInfo} />
          </ContentSection>
        </S.FeedbackSection>

        <S.FeedbackSection>
          <ContentSection title="나의 리뷰이 - 내가 리뷰해야 하는 분">
            <S.StyledDescription>
              리뷰이 피드백은 개발 역량에 한해서 진행합니다.
            </S.StyledDescription>
            <MyReviewee roomInfo={roomInfo} />
          </ContentSection>
        </S.FeedbackSection>
      </S.FeedbackContainer>

      <ContentSection title="함께 하는 참여자 살펴보기">
        <S.StyledDescription>
          해당 방에 같이 참여중인 인원 중 6명을 랜덤으로 보여줍니다. 새로고침 버튼을 통해 새로운
          리스트를 확인할 수 있습니다.
        </S.StyledDescription>
        <ParticipantList roomInfo={roomInfo} />
      </ContentSection>

      <ContentSection title="피드백 프로세스 설명보기">
        <S.ToggleWrapper>
          <FeedbackProcessInfo title="나의 리뷰이 프로세스">
            <p>리뷰이의 코드를 보고 리뷰를 남긴 뒤, 코드 역량에 대해 피드백을 작성합니다.</p>
            <ol>
              <li>리뷰이의 PR 링크를 클릭하여 코드 리뷰를 진행합니다.</li>
              <li>코드 리뷰를 완료한 뒤 코드리뷰 완료 버튼을 클릭합니다.</li>
              <li>피드백 작성 버튼을 클릭해 리뷰이의 개발 역량에 대해 피드백을 작성합니다.</li>
              <li>방이 종료되기 전까지 피드백 작성 및 수정이 가능합니다.</li>
            </ol>
          </FeedbackProcessInfo>

          <FeedbackProcessInfo title="나의 리뷰어 프로세스">
            <p>리뷰어가 남긴 코드 리뷰를 확인한 뒤, 소프트 스킬 역량에 대해 피드백을 작성합니다.</p>
            <ol>
              <li>리뷰어가 코드 리뷰를 완료했다면, 피드백 작성 버튼이 활성화됩니다.</li>
              <li>피드백 작성 버튼을 클릭해서 리뷰이의 개발 역량에 대해 피드백을 작성합니다.</li>
              <li>방이 종료되기 이전까지는 피드백 작성, 피드백 수정이 가능합니다.</li>
            </ol>
          </FeedbackProcessInfo>
        </S.ToggleWrapper>
      </ContentSection>
    </S.Layout>
  );
};

export default RoomDetailPage;
