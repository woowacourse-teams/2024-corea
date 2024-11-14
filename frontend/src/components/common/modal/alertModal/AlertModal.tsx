import Modal, { ModalProps } from "../Modal";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/modal/confirmModal/ConfirmModal.style";

type AlertModalProps = Omit<ModalProps, "onCancel" | "onConfirm">;

const AlertCustomStyle = {
  height: "fit-content",
};

const AlertModal = ({ ...rest }: AlertModalProps) => {
  return (
    <Modal style={AlertCustomStyle} {...rest}>
      <S.ConfirmModalContainer>{rest.children}</S.ConfirmModalContainer>
      <S.ButtonWrapper>
        <Button size="small" onClick={rest.onClose}>
          확인
        </Button>
      </S.ButtonWrapper>
    </Modal>
  );
};

export default AlertModal;
