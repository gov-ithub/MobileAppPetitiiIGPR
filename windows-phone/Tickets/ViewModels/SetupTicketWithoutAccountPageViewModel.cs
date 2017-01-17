using Caliburn.Micro;

namespace Tickets.ViewModels
{
    public class SetupTicketWithoutAccountPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public SetupTicketWithoutAccountPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void NextStep()
        {
            _navigationService.For<SendTicketWithoutAccountPageViewModel>().Navigate();
        }
    }
}
