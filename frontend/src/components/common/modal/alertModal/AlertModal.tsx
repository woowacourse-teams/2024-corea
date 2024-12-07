import Modal, { ModalProps } from "../Modal";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/modal/confirmModal/ConfirmModal.style";

interface AlertModalProps extends ModalProps {
  onConfirm: () => void;
  confirmButtonText?: string;
}

const AlertCustomStyle = {
  height: "fit-content",
};

const AlertModal = ({ confirmButtonText = "확인", ...rest }: AlertModalProps) => {
  return (
    <Modal style={AlertCustomStyle} {...rest}>
      <S.ConfirmModalContainer>{rest.children}</S.ConfirmModalContainer>
      <S.ButtonWrapper>
        <Button size="small" onClick={rest.onConfirm}>
          {confirmButtonText}
        </Button>
      </S.ButtonWrapper>
    </Modal>
  );
};

export default AlertModal;
