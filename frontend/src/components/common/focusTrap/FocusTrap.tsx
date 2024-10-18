import { Children, cloneElement, useEffect, useRef } from "react";

interface FocusTrapProps extends React.HTMLAttributes<HTMLDivElement> {
  children: React.ReactElement;
  onEscapeFocusTrap: () => void;
}

const getFocusableElements = (
  element: HTMLElement | ChildNode | null,
  result: HTMLElement[] = [],
) => {
  if (!element || !element.childNodes) return result;

  for (const childNode of element.childNodes) {
    const childElement = childNode as HTMLElement;
    if (childElement.tabIndex >= 0) {
      result.push(childElement);
    }
    getFocusableElements(childElement, result);
  }

  return result;
};

const FocusTrap = (props: FocusTrapProps) => {
  const { children, onEscapeFocusTrap, ...others } = props;
  const child = Children.only(children);

  const focusTrapRef = useRef<HTMLDivElement>(null);
  const focusableElements = useRef<(HTMLElement | null)[]>([]);
  const currentFocusIndex = useRef(-1);

  const Compo = cloneElement(child, {
    ...{ ...others, ...child?.props },
    tabIndex: -1,
    ref: focusTrapRef,
  });

  const focusNextElement = () => {
    currentFocusIndex.current = (currentFocusIndex.current + 1) % focusableElements.current.length;
    focusableElements.current[currentFocusIndex.current]?.focus();
  };

  const focusPrevElement = () => {
    currentFocusIndex.current =
      currentFocusIndex.current === 0
        ? focusableElements.current.length - 1
        : currentFocusIndex.current - 1;
    focusableElements.current[currentFocusIndex.current]?.focus();
  };

  const handleTabKeyDown = (event: KeyboardEvent) => {
    const isTabKeyDown = !event.shiftKey && event.key === "Tab";
    if (!isTabKeyDown) return;

    event.preventDefault();
    focusNextElement();
  };

  const handleShiftTabKeyDown = (event: KeyboardEvent) => {
    const isShiftTabKeyDown = event.shiftKey && event.key === "Tab";
    if (!isShiftTabKeyDown) return;

    event.preventDefault();
    focusPrevElement();
  };

  const handleEscapeKeyDown = (event: KeyboardEvent) => {
    if (event.key === "Escape") {
      onEscapeFocusTrap();
    }
  };

  useEffect(() => {
    const handleKeyPress = (event: KeyboardEvent) => {
      handleTabKeyDown(event);
      handleEscapeKeyDown(event);
      handleShiftTabKeyDown(event);
    };
    if (focusTrapRef.current) {
      focusableElements.current = getFocusableElements(focusTrapRef.current);
    }

    document.addEventListener("keydown", handleKeyPress);

    return () => {
      focusableElements.current = [];
      document.removeEventListener("keydown", handleKeyPress);
    };
  }, []);

  return <>{Compo}</>;
};

export default FocusTrap;
