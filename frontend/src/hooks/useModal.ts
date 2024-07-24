import { useState } from "react";

const useModal = () => {
  const [isOpen, setIsOpen] = useState(false);

  const handleOpenModal = () => {
    console.log("모달 열기");
    setIsOpen(true);
  };

  const handleCloseModal = () => {
    console.log("모달 닫기");

    setIsOpen(false);
  };

  return { isOpen, handleOpenModal, handleCloseModal };
};

export default useModal;
