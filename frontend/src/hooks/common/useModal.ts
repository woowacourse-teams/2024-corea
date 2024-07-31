import { useState } from "react";

const useModal = () => {
  const [isOpen, setIsOpen] = useState(false);

  const handleOpenModal = () => setIsOpen(true);

  const handleCloseModal = () => setIsOpen(false);

  return { isOpen, handleOpenModal, handleCloseModal };
};

export default useModal;
