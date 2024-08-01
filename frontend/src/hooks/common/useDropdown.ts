import { useEffect, useRef, useState } from "react";

const isDescendant = (parent: Node, child: Node | null): boolean => {
  let node = child;
  while (node !== null) {
    if (node === parent) {
      return true;
    }
    node = node.parentNode;
  }
  return false;
};

const useDropdown = () => {
  const [isOpen, setIsOpen] = useState(false);

  const handleToggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  return { isOpen, handleToggleDropdown };
};

export default useDropdown;
