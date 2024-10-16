import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useModal from "@/hooks/common/useModal";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import Button from "@/components/common/button/Button";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Dropdown, { DropdownItem } from "@/components/common/dropdown/Dropdown";
import { Input } from "@/components/common/input/Input";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import { Textarea } from "@/components/common/textarea/Textarea";
import DateTimePicker from "@/components/dateTimePicker/DateTimePicker";
import * as S from "@/pages/roomCreate/RoomCreatePage.style";
import { Classification, CreateRoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";
import { formatCombinedDateTime } from "@/utils/dateFormatter";

const initialFormState = {
  title: "",
  content: "",
  repositoryLink: "",
  thumbnailLink: "",
  matchingSize: 0,
  keywords: [],
  limitedParticipants: 0,
  recruitmentDeadline: new Date(),
  reviewDeadline: new Date(),
  classification: "",
};

const dropdownItems: DropdownItem[] = [
  { text: "안드로이드", value: "ANDROID" },
  { text: "백엔드", value: "BACKEND" },
  { text: "프론트엔드", value: "FRONTEND" },
];

const RoomCreatePage = () => {
  const navigate = useNavigate();
  const [isClickedButton, setIsClickedButton] = useState(false);
  const [formState, setFormState] = useState<CreateRoomInfo>(initialFormState);
  const { postCreateRoomMutation } = useMutateRoom();
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();

  const handleInputChange = <K extends keyof CreateRoomInfo>(name: K, value: CreateRoomInfo[K]) => {
    setFormState((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleConfirm = () => {
    const formattedFormState = {
      ...formState,
      recruitmentDeadline: formatCombinedDateTime(formState.recruitmentDeadline),
      reviewDeadline: formatCombinedDateTime(formState.reviewDeadline),
    };

    postCreateRoomMutation.mutate(formattedFormState, {
      onSuccess: () => navigate("/"),
    });

    setIsClickedButton(true);
    handleCloseModal();
  };

  return (
    <ContentSection title="방 생성하기">
      <ConfirmModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onConfirm={handleConfirm}
        onCancel={handleCloseModal}
      >
        {MESSAGES.GUIDANCE.CREATE_ROOM}
      </ConfirmModal>

      <S.CreateSection>
        <S.RowContainer>
          <S.ContentLabel>
            제목 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <Input
              name="title"
              value={formState.title}
              onChange={(e) => handleInputChange("title", e.target.value)}
              error={isClickedButton && formState.title === ""}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            분류 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <Dropdown
              dropdownItems={dropdownItems}
              selectedCategory={formState.classification}
              onSelectCategory={(value) =>
                handleInputChange("classification", value as Classification)
              }
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>내용</S.ContentLabel>
          <S.ContentInput>
            <Textarea
              name="content"
              value={formState.content}
              onChange={(e) => handleInputChange("content", e.target.value)}
              rows={5}
              showCharCount={true}
              maxLength={1000}
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            깃허브 레포지토리 링크 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <Input
              name="repositoryLink"
              value={formState.repositoryLink}
              onChange={(e) => handleInputChange("repositoryLink", e.target.value)}
              error={isClickedButton && formState.repositoryLink === ""}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>이미지 링크</S.ContentLabel>
          <S.ContentInput>
            <Input
              name="thumbnailLink"
              value={formState.thumbnailLink}
              onChange={(e) => handleInputChange("thumbnailLink", e.target.value)}
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>키워드 (콤마로 구분)</S.ContentLabel>
          <S.ContentInput>
            <Input
              name="keywords"
              value={formState.keywords}
              onChange={(e) =>
                handleInputChange(
                  "keywords",
                  e.target.value.split(",").map((keyword) => keyword.trim()),
                )
              }
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            상호 리뷰 인원 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <Input
              type="number"
              name="matchingSize"
              value={formState.matchingSize}
              onChange={(e) => handleInputChange("matchingSize", parseInt(e.target.value, 10))}
              error={isClickedButton && formState.matchingSize === 0}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            최대 참여 인원 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <Input
              type="number"
              name="limitedParticipants"
              value={formState.limitedParticipants}
              onChange={(e) =>
                handleInputChange("limitedParticipants", parseInt(e.target.value, 10))
              }
              error={isClickedButton && formState.limitedParticipants === 0}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            모집 마감일 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <DateTimePicker
              selectedDateTime={formState.recruitmentDeadline}
              onDateTimeChange={(newDateTime) =>
                handleInputChange("recruitmentDeadline", newDateTime)
              }
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            코드 리뷰 마감일 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <DateTimePicker
              selectedDateTime={formState.reviewDeadline}
              onDateTimeChange={(newDateTime) => handleInputChange("reviewDeadline", newDateTime)}
            />
          </S.ContentInput>
        </S.RowContainer>

        <Button onClick={handleOpenModal}>완료</Button>
      </S.CreateSection>
    </ContentSection>
  );
};

export default RoomCreatePage;
