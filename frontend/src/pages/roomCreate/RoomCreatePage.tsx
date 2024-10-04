import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useModal from "@/hooks/common/useModal";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import Button from "@/components/common/button/Button";
import CalendarDropdown from "@/components/common/calendarDropdown/CalendarDropdown";
import ContentSection from "@/components/common/contentSection/ContentSection";
import { Input } from "@/components/common/input/Input";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import { Textarea } from "@/components/common/textarea/Textarea";
import { TimeDropdown } from "@/components/common/timeDropdown/TimeDropdown";
import * as S from "@/pages/roomCreate/RoomCreatePage.style";
import { CreateRoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";
import { formatCombinedDateTime } from "@/utils/dateFormatter";

const initialFormState: CreateRoomInfo = {
  title: "",
  content: "",
  repositoryLink: "",
  thumbnailLink: "",
  matchingSize: 0,
  keywords: [],
  limitedParticipants: 0,
  recruitmentDeadline: formatCombinedDateTime(new Date(), new Date()),
  reviewDeadline: formatCombinedDateTime(new Date(), new Date()),
  classification: "",
};

const RoomCreatePage = () => {
  const navigate = useNavigate();

  const [isClickedButton, setIsClickedButton] = useState(false);
  const [formState, setFormState] = useState<CreateRoomInfo>(initialFormState);

  const [recruitmentDate, setRecruitmentDate] = useState(new Date());
  const [reviewDate, setReviewDate] = useState(new Date());
  const [recruitmentTime, setRecruitmentTime] = useState(new Date());
  const [reviewTime, setReviewTime] = useState(new Date());

  const { postCreateRoomMutation } = useMutateRoom();
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    let newValue: string | string[] | number;
    if (name === "keywords") {
      newValue = value.split(",").map((keyword) => keyword.trim());
    } else if (name === "matchingSize" || name === "limitedParticipants") {
      newValue = value === "" ? 0 : parseInt(value, 10);
    } else {
      newValue = value;
    }
    setFormState((prevState) => ({
      ...prevState,
      [name]: newValue,
    }));
  };

  const updateFormStateWithDateTime = (
    field: "recruitmentDeadline" | "reviewDeadline",
    date: Date,
    time: Date,
  ) => {
    setFormState((prev) => ({
      ...prev,
      [field]: formatCombinedDateTime(date, time),
    }));
  };

  const handleRecruitmentDateChange = (date: Date) => {
    setRecruitmentDate(date);
    updateFormStateWithDateTime("recruitmentDeadline", date, recruitmentTime);
  };

  const handleRecruitmentTimeChange = (time: Date) => {
    setRecruitmentTime(time);
    updateFormStateWithDateTime("recruitmentDeadline", recruitmentDate, time);
  };

  const handleReviewDateChange = (date: Date) => {
    setReviewDate(date);
    updateFormStateWithDateTime("reviewDeadline", date, reviewTime);
  };

  const handleReviewTimeChange = (time: Date) => {
    setReviewTime(time);
    updateFormStateWithDateTime("reviewDeadline", reviewDate, time);
  };

  const handleConfirm = () => {
    postCreateRoomMutation.mutate(formState, {
      onSuccess: () => navigate("/"),
    });
    setIsClickedButton(true);
    handleCloseModal();
  };

  return (
    <ContentSection title="방 생성하기">
      <ConfirmModal
        isOpen={isOpen}
        onClose={handleCloseModal}
        onConfirm={handleConfirm}
        onCancel={handleCloseModal}
      >
        {MESSAGES.GUIDANCE.CREATE_ROOM}
      </ConfirmModal>

      <S.CreateSection>
        <S.RowContainer>
          <S.ContentLabel>
            제목<span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <Input
              name="title"
              value={formState.title}
              onChange={handleInputChange}
              error={isClickedButton && formState.title === ""}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            분류(FRONTEND, BACKEND, ANDROID) <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <Input
              name="classification"
              value={formState.classification}
              onChange={handleInputChange}
              error={isClickedButton && formState.classification === ""}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>내용</S.ContentLabel>
          <S.ContentInput>
            <Textarea
              name="content"
              value={formState.content}
              onChange={handleInputChange}
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
              onChange={handleInputChange}
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
              onChange={handleInputChange}
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>키워드 (콤마로 구분)</S.ContentLabel>
          <S.ContentInput>
            <Input name="keywords" value={formState.keywords} onChange={handleInputChange} />
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
              onChange={handleInputChange}
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
              onChange={handleInputChange}
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
            <CalendarDropdown
              selectedDate={recruitmentDate}
              handleSelectedDate={handleRecruitmentDateChange}
              error={isClickedButton && formState.recruitmentDeadline === ""}
              required
            ></CalendarDropdown>
            <TimeDropdown
              selectedTime={recruitmentTime}
              onTimeChange={handleRecruitmentTimeChange}
              error={isClickedButton && formState.recruitmentDeadline === ""}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <S.RowContainer>
          <S.ContentLabel>
            코드 리뷰 마감일 <span>*필수입력</span>
          </S.ContentLabel>
          <S.ContentInput>
            <CalendarDropdown
              selectedDate={reviewDate}
              handleSelectedDate={handleReviewDateChange}
              error={isClickedButton && formState.reviewDeadline === ""}
              required
            ></CalendarDropdown>
            <TimeDropdown
              selectedTime={reviewTime}
              onTimeChange={handleReviewTimeChange}
              error={isClickedButton && formState.reviewDeadline === ""}
              required
            />
          </S.ContentInput>
        </S.RowContainer>

        <Button onClick={handleOpenModal}>완료</Button>
      </S.CreateSection>
    </ContentSection>
  );
};

export default RoomCreatePage;
