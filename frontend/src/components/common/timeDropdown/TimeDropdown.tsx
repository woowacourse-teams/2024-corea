import { InputHTMLAttributes } from "react";
import useDropdown from "@/hooks/common/useDropdown";
import * as S from "@/components/common/timeDropdown/TimeDropdown.style";
import { formatTime } from "@/utils/dateFormatter";

interface TimeDropdownProps extends InputHTMLAttributes<HTMLInputElement> {
  selectedTime: Date;
  onTimeChange: (time: Date) => void;
  error?: boolean;
}

interface TimeDropdownChangeProps {
  newTime: Date;
  canCloseDropdown: boolean;
}

const TimePicker = ({
  time,
  onTimeInputChange,
}: {
  time: Date;
  onTimeInputChange: (event: TimeDropdownChangeProps) => void;
}) => {
  return (
    <S.TimePickerWrapper>
      <S.TimeSelector>
        {Array.from({ length: 24 }).map((_, hour) => (
          <S.TimeButton
            key={hour}
            isActive={hour === time.getHours()}
            onClick={() => {
              const newTime = new Date(time);
              newTime.setHours(hour);
              onTimeInputChange({ newTime, canCloseDropdown: false });
            }}
          >
            {hour < 10 ? `0${hour}` : hour}
          </S.TimeButton>
        ))}
      </S.TimeSelector>

      <S.TimeSelector>
        {Array.from({ length: 60 }).map((_, minute) => (
          <S.TimeButton
            key={minute}
            isActive={minute === time.getMinutes()}
            onClick={() => {
              const newTime = new Date(time);
              newTime.setMinutes(minute);
              onTimeInputChange({ newTime, canCloseDropdown: true });
            }}
          >
            {minute < 10 ? `0${minute}` : minute}
          </S.TimeButton>
        ))}
      </S.TimeSelector>
    </S.TimePickerWrapper>
  );
};

export const TimeDropdown = ({
  selectedTime,
  onTimeChange,
  error = false,
  ...rest
}: TimeDropdownProps) => {
  const { isDropdownOpen, handleToggleDropdown, dropdownRef } = useDropdown();

  const handleTimeChange = ({ newTime, canCloseDropdown }: TimeDropdownChangeProps) => {
    onTimeChange(newTime);

    if (canCloseDropdown) handleToggleDropdown();
  };

  return (
    <S.TimeDropdownContainer ref={dropdownRef}>
      <S.TimeDropdownToggle
        type="text"
        value={formatTime(selectedTime)}
        onClick={handleToggleDropdown}
        placeholder="시간을 선택하세요"
        readOnly
        $error={error}
        {...rest}
      />
      {isDropdownOpen && <TimePicker time={selectedTime} onTimeInputChange={handleTimeChange} />}
    </S.TimeDropdownContainer>
  );
};
