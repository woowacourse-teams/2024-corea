import Modal, { ModalProps } from "../Modal";

const FeedbackModalCustomStyle = {
  height: "70vh",
  maxHeight: "800px",
};

const FeedbackCustomModal = ({ ...rest }: ModalProps) => {
  return (
    <Modal style={FeedbackModalCustomStyle} {...rest}>
      {rest.children}
    </Modal>
  );
};

export default FeedbackCustomModal;
