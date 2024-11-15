import React from "react";
import { useNavigate } from "react-router-dom";
import useDropdown from "@/hooks/common/useDropdown";
import useModal from "@/hooks/common/useModal";
import useMutateRoom from "@/hooks/mutations/useMutateRoom";
import FocusTrap from "@/components/common/focusTrap/FocusTrap";
import Icon from "@/components/common/icon/Icon";
import ConfirmModal from "@/components/common/modal/confirmModal/ConfirmModal";
import * as S from "@/components/roomDetailPage/controlButton/ControlButton.style";
import { ParticipationStatus, RoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";

export type DropdownItem = {
  name: string;
  action: string;
};

export const dropdownItemsConfig: Record<string, DropdownItem[]> = {
  MANAGER: [
    { name: "수정하기", action: "editRoom" },
    { name: "삭제하기", action: "deleteRoom" },
  ],
  PARTICIPATED: [{ name: "방 나가기", action: "exitRoom" }],
};

interface ControlButtonProps {
  roomInfo: RoomInfo;
  participationStatus: ParticipationStatus;
}

const ControlButton = ({ roomInfo, participationStatus }: ControlButtonProps) => {
  const navigate = useNavigate();
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();
  const { deleteParticipateInMutation, deleteParticipatedRoomMutation } = useMutateRoom();

  const dropdownItems = dropdownItemsConfig[participationStatus] || [];

  const handleControlButtonClick = (event: React.MouseEvent) => {
    event.preventDefault();
    handleToggleDropdown();
  };

  const handleDropdownItemClick = (action: string) => {
    switch (action) {
      case "editRoom":
        navigate(`/rooms/edit/${roomInfo.id}`);
        break;
      case "deleteRoom":
        handleOpenModal();
        break;
      case "exitRoom":
        handleOpenModal();
        break;
      default:
        break;
    }
  };

  const handleConfirm = () => {
    if (roomInfo.participationStatus === "MANAGER") {
      deleteParticipatedRoomMutation.mutate(roomInfo.id, {
        onSuccess: () => navigate("/"),
      });
      return;
    }
    deleteParticipateInMutation.mutate(roomInfo.id, {
      onSuccess: () => navigate("/"),
    });
  };

  return (
    <>
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

      <S.ControlButtonContainer ref={dropdownRef}>
        <S.IconWrapper $isOpen={isDropdownOpen}>
          <Icon kind="control" size="3rem" color="grey" onClick={handleControlButtonClick} />
        </S.IconWrapper>

        {isDropdownOpen && (
          <S.DropdownMenu>
            <FocusTrap onEscapeFocusTrap={() => handleToggleDropdown()}>
              <S.DropdownItemWrapper>
                {dropdownItems.map((item: DropdownItem) => (
                  <S.DropdownItem
                    key={item.name}
                    onClick={() => handleDropdownItemClick(item.action)}
                    tabIndex={0}
                    onKeyDown={(e) => {
                      if (e.key === "Enter") handleDropdownItemClick(item.action);
                    }}
                  >
                    {item.name}
                  </S.DropdownItem>
                ))}
              </S.DropdownItemWrapper>
            </FocusTrap>
          </S.DropdownMenu>
        )}
      </S.ControlButtonContainer>
    </>
  );
};

export default ControlButton;
