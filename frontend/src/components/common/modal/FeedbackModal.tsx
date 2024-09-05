import Modal, { ModalProps } from "./Modal";

const FeedbackModalCustomStyle = {
  maxWidth: "500px",
  height: "70vh",
  maxHeight: "800px",
};

const FeedbackModal = ({ ...rest }: ModalProps) => {
  return (
    <Modal style={FeedbackModalCustomStyle} {...rest}>
      {rest.children}
    </Modal>
  );
};

export default FeedbackModal;
