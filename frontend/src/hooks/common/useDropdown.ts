import { useEffect, useRef, useState } from "react";

const useDropdown = () => {
  const [isOpen, setIsOpen] = useState(false);

  const handleToggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  return { isOpen, handleToggleDropdown };
};

export default useDropdown;
