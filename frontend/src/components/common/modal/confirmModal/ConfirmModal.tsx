import Modal, { ModalProps } from "../Modal";
import Button from "@/components/common/button/Button";
import * as S from "@/components/common/modal/confirmModal/ConfirmModal.style";

const ConfirmCustomStyle = {
  display: "flex",
  flexDirection: "column",
  height: "fit-content",
  gap: "5rem",
  padding: "3rem",
  fontSize: "1.6rem",
  fontWeight: 700,
};

interface ConfirmModalProps extends ModalProps {
  confirmButtonText?: string;
  cancelButtonText?: string;
}

const ConfirmModal = ({
  confirmButtonText = "확인",
  cancelButtonText = "취소",
  ...rest
}: ConfirmModalProps) => {
  return (
    <Modal style={ConfirmCustomStyle as React.CSSProperties} {...rest}>
      {rest.children}
      <S.ButtonWrapper>
        <Button size="small" onClick={rest.onCancel} className="cancel-button">
          {cancelButtonText}
        </Button>
        <Button size="small" onClick={rest.onConfirm} className="confirm-button">
          {confirmButtonText}
        </Button>
      </S.ButtonWrapper>
    </Modal>
  );
};

export default ConfirmModal;
