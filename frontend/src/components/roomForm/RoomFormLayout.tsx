import Keyword from "../common/keyword/Keyword";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import useModal from "@/hooks/common/useModal";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import Button from "@/components/common/button/Button";
import Dropdown, { DropdownItem } from "@/components/common/dropdown/Dropdown";
import { Input } from "@/components/common/input/Input";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import { Textarea } from "@/components/common/textarea/Textarea";
import DateTimePicker from "@/components/dateTimePicker/DateTimePicker";
import * as S from "@/components/roomForm/RoomFormLayout.style";
import { Classification, CreateRoomInfo, RoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";
import { ErrorText } from "@/styles/common";
import { formatCombinedDateTime } from "@/utils/dateFormatter";
import validators from "@/utils/roomInputValidator";

const dropdownItems: DropdownItem[] = [
  { text: "안드로이드", value: "ANDROID" },
  { text: "백엔드", value: "BACKEND" },
  { text: "프론트엔드", value: "FRONTEND" },
];

interface RoomFormLayoutProps {
  formType: "create" | "edit";
  roomId?: number;
  data?: RoomInfo;
}

const getInitialFormState = (data?: RoomInfo): CreateRoomInfo => ({
  title: data?.title || "",
  classification: data?.classification || ("" as Classification),
  content: data?.content || "",
  repositoryLink: data?.repositoryLink || "",
  thumbnailLink: data?.thumbnailLink || "",
  keywords: data?.keywords.filter((keyword) => keyword !== "") || [],
  matchingSize: data?.matchingSize || 1,
  limitedParticipants: data?.limitedParticipants || 1,
  recruitmentDeadline: data ? new Date(data.recruitmentDeadline) : new Date(),
  reviewDeadline: data ? new Date(data.reviewDeadline) : new Date(),
  isPrivate: data?.isPrivate || false,
});

const RoomFormLayout = ({ formType, roomId, data }: RoomFormLayoutProps) => {
  const navigate = useNavigate();
  const [isClickedButton, setIsClickedButton] = useState(false);
  const [formState, setFormState] = useState<CreateRoomInfo>(() => getInitialFormState(data));
  const { postCreateRoomMutation, putEditRoomMutation } = useMutateRoom();
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();

  const isFormValid =
    validators.title(formState.title) === "" &&
    validators.classification(formState.classification) === "" &&
    validators.content(formState.content) === "" &&
    validators.repositoryLink(formState.repositoryLink) === "" &&
    validators.thumbnailLink(formState.thumbnailLink) === "" &&
    validators.keywords(formState.keywords) === "" &&
    validators.matchingSize(formState.matchingSize) === "" &&
    validators.limitedParticipants(formState.limitedParticipants, formState.matchingSize) === "" &&
    validators.recruitmentDeadline(formState.recruitmentDeadline) === "" &&
    validators.reviewDeadline(formState.reviewDeadline, formState.recruitmentDeadline) === "";

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

    if (formType === "edit" && roomId) {
      const updatedFormState = { ...formattedFormState, roomId };
      putEditRoomMutation.mutate(updatedFormState, {
        onSuccess: () => navigate(`/rooms/${roomId}`),
      });
    } else {
      postCreateRoomMutation.mutate(formattedFormState, {
        onSuccess: () => navigate("/"),
      });
    }

    handleCloseModal();
  };

  return (
    <>
      <ConfirmModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onConfirm={handleConfirm}
        onCancel={handleCloseModal}
      >
        {formType === "create" ? MESSAGES.GUIDANCE.CREATE_ROOM : MESSAGES.GUIDANCE.EDIT_ROOM}
      </ConfirmModal>

      <S.RoomFormContainer>
        <S.SectionTitle>
          {formType === "create" ? "방 생성하기" : "방 정보 수정하기"}
          <S.HelpText>
            <S.RequiredLabel>*</S.RequiredLabel>표시는 필수 입력 항목입니다.
          </S.HelpText>
        </S.SectionTitle>

        <S.SubSection>
          <S.RowContainer>
            <S.ContentLabel>
              제목 <S.RequiredLabel>*</S.RequiredLabel>
            </S.ContentLabel>
            <S.ContentInput>
              <Input
                name="title"
                value={formState.title}
                onChange={(e) => handleInputChange("title", e.target.value)}
                showCharCount={true}
                maxLength={50}
                error={isClickedButton && validators.title(formState.title) !== ""}
                required
              />
              <ErrorText>{isClickedButton && validators.title(formState.title)}</ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>
              분류 <S.RequiredLabel>*</S.RequiredLabel>
            </S.ContentLabel>
            <S.ContentInput>
              <Dropdown
                name="classification"
                dropdownItems={dropdownItems}
                selectedCategory={formState.classification}
                onSelectCategory={(value) =>
                  handleInputChange("classification", value as Classification)
                }
                error={
                  isClickedButton && validators.classification(formState.classification) !== ""
                }
              />
              <ErrorText>
                {isClickedButton && validators.classification(formState.classification)}
              </ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>
              깃허브 레포지토리 링크 <S.RequiredLabel>*</S.RequiredLabel>
            </S.ContentLabel>
            <S.HelpText>.git 확장자가 아닌 링크를 넣어주세요.</S.HelpText>
            <S.ContentInput>
              <Input
                name="repositoryLink"
                value={formState.repositoryLink}
                placeholder="https://github.com/username/repository"
                onChange={(e) => handleInputChange("repositoryLink", e.target.value)}
                error={
                  isClickedButton && validators.repositoryLink(formState.repositoryLink) !== ""
                }
                required
              />
              <ErrorText>
                {isClickedButton && validators.repositoryLink(formState.repositoryLink)}
              </ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>내용</S.ContentLabel>
            <S.ContentInput>
              <Textarea
                name="content"
                value={formState.content}
                onChange={(e) => handleInputChange("content", e.target.value)}
                placeholder="방 설명, 코드 리뷰 방향성 등을 적어주세요"
                rows={5}
                showCharCount={true}
                maxLength={4000}
                error={isClickedButton && validators.content(formState.content) !== ""}
              />
              <ErrorText>{isClickedButton && validators.content(formState.content)}</ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>키워드</S.ContentLabel>
            <S.ContentInput>
              <Keyword
                currentKeywords={formState.keywords}
                onKeywordsChange={(keywords) => handleInputChange("keywords", keywords)}
                error={isClickedButton && validators.keywords(formState.keywords) !== ""}
              />
              <ErrorText>{isClickedButton && validators.keywords(formState.keywords)}</ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>이미지 링크</S.ContentLabel>
            <S.ContentInput>
              <Input
                name="thumbnailLink"
                value={formState.thumbnailLink}
                onChange={(e) => handleInputChange("thumbnailLink", e.target.value)}
                error={isClickedButton && validators.thumbnailLink(formState.thumbnailLink) !== ""}
              />
              <ErrorText>
                {isClickedButton && validators.thumbnailLink(formState.thumbnailLink)}
              </ErrorText>
            </S.ContentInput>
          </S.RowContainer>
        </S.SubSection>

        <S.SubSection>
          <S.RowContainer>
            <S.ContentLabel>
              상호 리뷰 인원 <S.RequiredLabel>*</S.RequiredLabel>
            </S.ContentLabel>
            <S.HelpText>최소 1명, 최대 5명 가능해요.</S.HelpText>
            <S.ContentInput>
              <Input
                type="number"
                min="1"
                max="5"
                name="matchingSize"
                value={formState.matchingSize}
                onChange={(e) => handleInputChange("matchingSize", parseInt(e.target.value, 10))}
                error={isClickedButton && validators.matchingSize(formState.matchingSize) !== ""}
                required
              />
              <ErrorText>
                {isClickedButton && validators.matchingSize(formState.matchingSize)}
              </ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>
              최대 참여 인원 <S.RequiredLabel>*</S.RequiredLabel>
            </S.ContentLabel>
            <S.ContentInput>
              <Input
                type="number"
                min={formState.matchingSize}
                name="limitedParticipants"
                value={formState.limitedParticipants}
                onChange={(e) =>
                  handleInputChange("limitedParticipants", parseInt(e.target.value, 10))
                }
                error={
                  isClickedButton &&
                  validators.limitedParticipants(
                    formState.limitedParticipants,
                    formState.matchingSize,
                  ) !== ""
                }
                required
              />
              <ErrorText>
                {isClickedButton &&
                  validators.limitedParticipants(
                    formState.limitedParticipants,
                    formState.matchingSize,
                  )}
              </ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>
              모집 마감일 <S.RequiredLabel>*</S.RequiredLabel>
            </S.ContentLabel>
            <S.ContentInput>
              <DateTimePicker
                selectedDateTime={formState.recruitmentDeadline}
                onDateTimeChange={(newDateTime) => {
                  handleInputChange("recruitmentDeadline", newDateTime);
                  if (newDateTime > formState.reviewDeadline) {
                    const newDate = new Date();
                    newDate.setFullYear(newDateTime.getFullYear());
                    newDate.setMonth(newDateTime.getMonth());
                    newDate.setDate(newDateTime.getDate());
                    handleInputChange("reviewDeadline", newDate);
                  }
                }}
                options={{ isPastDateDisabled: true }}
                error={
                  isClickedButton &&
                  validators.recruitmentDeadline(formState.recruitmentDeadline) !== ""
                }
              />
              <ErrorText>
                {isClickedButton && validators.recruitmentDeadline(formState.recruitmentDeadline)}
              </ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>
              코드 리뷰 마감일 <S.RequiredLabel>*</S.RequiredLabel>
            </S.ContentLabel>
            <S.ContentInput>
              <DateTimePicker
                selectedDateTime={formState.reviewDeadline}
                onDateTimeChange={(newDateTime) => handleInputChange("reviewDeadline", newDateTime)}
                options={{
                  isPastDateDisabled: true,
                  disabledBeforeDate: formState.recruitmentDeadline,
                }}
                error={
                  isClickedButton &&
                  validators.reviewDeadline(
                    formState.reviewDeadline,
                    formState.recruitmentDeadline,
                  ) !== ""
                }
              />
              <ErrorText>
                {isClickedButton &&
                  validators.reviewDeadline(
                    formState.reviewDeadline,
                    formState.recruitmentDeadline,
                  )}
              </ErrorText>
            </S.ContentInput>
          </S.RowContainer>

          <S.RowContainer>
            <S.ContentLabel>방 공개 여부</S.ContentLabel>
            <S.ContentWrapper>
              <S.ContentRadioInput
                type="radio"
                id="yes"
                name="isPrivate"
                checked={formState.isPrivate}
                onChange={() => handleInputChange("isPrivate", true)}
              />
              <S.RadioLabel htmlFor="yes">예</S.RadioLabel>
              <S.ContentRadioInput
                type="radio"
                id="no"
                name="isPrivate"
                checked={!formState.isPrivate}
                onChange={() => handleInputChange("isPrivate", false)}
              />
              <S.RadioLabel htmlFor="no">아니요</S.RadioLabel>
            </S.ContentWrapper>
          </S.RowContainer>
        </S.SubSection>

        <Button
          onClick={() => {
            if (isFormValid) handleOpenModal();
            setIsClickedButton(true);
          }}
        >
          완료
        </Button>
      </S.RoomFormContainer>
    </>
  );
};

export default RoomFormLayout;
