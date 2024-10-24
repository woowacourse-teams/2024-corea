import useDropdown from "@/hooks/common/useDropdown";
import Icon from "@/components/common/icon/Icon";
import * as S from "@/components/roomDetailPage/feedbackProcessInfo/FeedbackProcessInfo.style";

interface ExpandableSectionProps {
  title: string;
  children: React.ReactNode;
}

const FeedbackProcessInfo = ({ title, children }: ExpandableSectionProps) => {
  const { isDropdownOpen, handleToggleDropdown } = useDropdown();

  const handleProcessInfoClick = (event: React.MouseEvent | React.KeyboardEvent) => {
    event.preventDefault();
    if ("key" in event && event.key !== "Enter" && event.key !== " ") return;
    handleToggleDropdown();
  };

  return (
    <S.ExpandableSection>
      <S.StyledTitle
        onClick={handleProcessInfoClick}
        onKeyDown={handleProcessInfoClick}
        tabIndex={0}
        role="button"
        aria-expanded={isDropdownOpen}
      >
        {title}
        <Icon kind={isDropdownOpen ? "arrowDropUp" : "arrowDropDown"} size="2rem" />
      </S.StyledTitle>
      {isDropdownOpen && <S.ExpandableContent role="region">{children}</S.ExpandableContent>}
    </S.ExpandableSection>
  );
};

export default FeedbackProcessInfo;
