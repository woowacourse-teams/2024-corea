import { useSuspenseQuery } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useModal from "@/hooks/common/useModal";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Icon from "@/components/common/icon/Icon";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import MyReviewee from "@/components/roomDetailPage/myReviewee/MyReviewee";
import MyReviewer from "@/components/roomDetailPage/myReviewer/MyReviewer";
import ParticipantList from "@/components/roomDetailPage/participantList/ParticipantList";
import RoomInfoCard from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard";
import * as S from "@/pages/roomDetail/RoomDetailPage.style";
import QUERY_KEYS from "@/apis/queryKeys";
import { getRoomDetailInfo } from "@/apis/rooms.api";
import MESSAGES from "@/constants/message";

const RoomDetailPage = () => {
  const params = useParams();
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();
  const roomId = params.id ? Number(params.id) : 0;
  const [isReviewerInfoExpanded, setIsReviewerInfoExpanded] = useState(false);
  const [isRevieweeInfoExpanded, setIsRevieweeInfoExpanded] = useState(false);
  const { deleteParticipateInMutation, deleteParticipatedRoomMutation } = useMutateRoom();
  const navigate = useNavigate();

  const { data: roomInfo } = useSuspenseQuery({
    queryKey: [QUERY_KEYS.ROOM_DETAIL_INFO, roomId],
    queryFn: () => getRoomDetailInfo(roomId),
  });

  const toggleReviewerInfoExpand = () => {
    setIsReviewerInfoExpanded(!isReviewerInfoExpanded);
  };

  const toggleRevieweeInfoExpand = () => {
    setIsRevieweeInfoExpanded(!isRevieweeInfoExpanded);
  };

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
    roomInfo.roomStatus === "OPEN"
      ? {
          button: {
            label: roomInfo.participationStatus === "MANAGER" ? "방 삭제하기" : "방 나가기",
            onClick: handleOpenModal,
          },
        }
      : {};

  return (
    <S.Layout>
      <ConfirmModal
        isOpen={isOpen}
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
          <S.ExpandableSection
            onClick={toggleReviewerInfoExpand}
            $isExpanded={isReviewerInfoExpanded}
          >
            <S.StyledSquare>
              나의 리뷰이 프로세스
              <Icon kind={isReviewerInfoExpanded ? "arrowDropUp" : "arrowDropDown"} size="2rem" />
            </S.StyledSquare>
            {isReviewerInfoExpanded && (
              <S.ExpandableContent>
                <p>리뷰이의 코드를 보고 리뷰를 남긴 뒤, 코드 역량에 대해 피드백을 작성합니다.</p>
                <ol>
                  <li>리뷰이의 PR 링크를 클릭하여 코드 리뷰를 진행합니다.</li>
                  <li>코드 리뷰를 완료한 뒤 코드리뷰 완료 버튼을 클릭합니다.</li>
                  <li>피드백 작성 버튼을 클릭해 리뷰이의 개발 역량에 대해 피드백을 작성합니다.</li>
                  <li>방이 종료되기 전까지 피드백 작성 및 수정이 가능합니다.</li>
                </ol>
              </S.ExpandableContent>
            )}
          </S.ExpandableSection>

          <S.ExpandableSection
            onClick={toggleRevieweeInfoExpand}
            $isExpanded={isRevieweeInfoExpanded}
          >
            <S.StyledSquare>
              나의 리뷰어 프로세스
              <Icon kind={isRevieweeInfoExpanded ? "arrowDropUp" : "arrowDropDown"} size="2rem" />
            </S.StyledSquare>
            {isRevieweeInfoExpanded && (
              <S.ExpandableContent>
                <p>
                  리뷰어가 남긴 코드 리뷰를 확인한 뒤, 소프트 스킬 역량에 대해 피드백을 작성합니다.
                </p>
                <ol>
                  <li>리뷰어가 코드 리뷰를 완료했다면, 피드백 작성 버튼이 활성화됩니다.</li>
                  <li>
                    피드백 작성 버튼을 클릭해서 리뷰이의 개발 역량에 대해 피드백을 작성합니다.
                  </li>
                  <li>방이 종료되기 이전까지는 피드백 작성, 피드백 수정이 가능합니다.</li>
                </ol>
              </S.ExpandableContent>
            )}
          </S.ExpandableSection>
        </S.ToggleWrapper>
      </ContentSection>
    </S.Layout>
  );
};

export default RoomDetailPage;
