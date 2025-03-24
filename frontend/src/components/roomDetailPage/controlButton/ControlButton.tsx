import React, { useState } from "react";
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

type Action = "EXIT_ROOM" | "DELETE_ROOM";

export interface DropdownItem {
  name: string;
  action: Action;
}

export const dropdownItemsConfig: Record<string, DropdownItem[]> = {
  MANAGER: [
    // { name: "수정하기", action: "EDIT_ROOM" },
    { name: "삭제하기", action: "DELETE_ROOM" },
  ],
  PARTICIPATED: [{ name: "방 나가기", action: "EXIT_ROOM" }],
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
  const [selectedAction, setSelectedAction] = useState<Action>("" as Action);

  const dropdownItems = dropdownItemsConfig[participationStatus] || [];

  const handleControlButtonClick = (event: React.MouseEvent) => {
    event.preventDefault();
    handleToggleDropdown();
  };

  const handleDropdownItemClick = (action: string) => {
    setSelectedAction(action as Action);
    if (action === "EDIT_ROOM") {
      navigate(`/rooms/edit/${roomInfo.id}`);
    } else {
      handleOpenModal();
    }
  };

  const handleConfirm = () => {
    if (selectedAction === "DELETE_ROOM") {
      deleteParticipatedRoomMutation.mutate(roomInfo.id, {
        onSuccess: () => navigate("/"),
      });
    }
    if (selectedAction === "EXIT_ROOM") {
      deleteParticipateInMutation.mutate(roomInfo.id, {
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
        {selectedAction && MESSAGES.GUIDANCE[selectedAction]}
      </ConfirmModal>

      <S.ControlButtonContainer ref={dropdownRef}>
        <S.IconWrapper $isOpen={isDropdownOpen}>
          <Icon kind="control" size="3rem" color="grey" onClick={handleControlButtonClick} />
        </S.IconWrapper>

        {isDropdownOpen && (
          <S.DropdownMenu>
            <FocusTrap onEscapeFocusTrap={() => handleToggleDropdown()}>
              <S.DropdownItemWrapper>
                {dropdownItems.map((item: DropdownItem, index) => (
                  <React.Fragment key={item.name}>
                    <S.DropdownItem
                      onClick={() => handleDropdownItemClick(item.action)}
                      tabIndex={0}
                      onKeyDown={(e) => {
                        if (e.key === "Enter") handleDropdownItemClick(item.action);
                      }}
                    >
                      {item.name}
                    </S.DropdownItem>
                    {index < dropdownItems.length - 1 && <S.Divider />}
                  </React.Fragment>
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
